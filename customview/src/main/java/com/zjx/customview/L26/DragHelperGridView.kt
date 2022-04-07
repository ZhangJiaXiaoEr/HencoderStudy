package com.zjx.customview.L26

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.customview.widget.ViewDragHelper

class DragHelperGridView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private val columns = 2
    private val rows = 3
    private val dragHelper: ViewDragHelper = ViewDragHelper.create(this, DragCallback())
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = specWidth/columns
        val childHeight = specHeight/rows
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY))
        setMeasuredDimension(specWidth, specHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft: Int
        var childTop: Int
        val childWidth = width/columns
        val childHeight = height/rows
        for ((index, child) in children.withIndex()) {
            childLeft = index%columns * childWidth
            childTop = index/columns * childHeight
            child.layout(childLeft, childTop, childLeft+childWidth, childTop+childHeight)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev!!)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        dragHelper.processTouchEvent(event!!)
        return true
    }

    private var captureviewLeft = 0
    private var captureviewTop = 0

    private inner class DragCallback: ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        /**
         * 对水平方向的移动进行钳制，默认不能水平滑动
         */
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }

        /**
         * 对垂直方向的移动进行钳制，默认不能垂直滑动
         */
        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        /**
         * 拖动状态改变
         */
        override fun onViewDragStateChanged(state: Int) {
            when(state) {
                ViewDragHelper.STATE_DRAGGING->{}//被拖动
                ViewDragHelper.STATE_SETTLING->{}//自滚动
                ViewDragHelper.STATE_IDLE->{ dragHelper.capturedView!!.elevation --}
            }
        }

        /**\
         * capturedView 的位置发生变化
         */
        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
        }

        /**
         * 当captureview被捕获时回调
         */
        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            capturedChild.elevation += 1
            captureviewLeft = capturedChild.left
            captureviewTop = capturedChild.top
        }

        /**
         * 当触摸到边界时回调。
         */
        override fun onEdgeTouched(edgeFlags: Int, pointerId: Int) {
            super.onEdgeTouched(edgeFlags, pointerId)
        }

        /**
         * 在边界拖动时调用
         * 需要设置dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)
         */
        override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
            super.onEdgeDragStarted(edgeFlags, pointerId)
        }

        /**
         * 结束拖动，手指抬起
         */
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            dragHelper.settleCapturedViewAt(captureviewLeft, captureviewTop)
            postInvalidateOnAnimation()
        }
    }

    override fun computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }
}