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
         * ???????????????????????????????????????????????????????????????
         */
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }

        /**
         * ???????????????????????????????????????????????????????????????
         */
        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        /**
         * ??????????????????
         */
        override fun onViewDragStateChanged(state: Int) {
            when(state) {
                ViewDragHelper.STATE_DRAGGING->{}//?????????
                ViewDragHelper.STATE_SETTLING->{}//?????????
                ViewDragHelper.STATE_IDLE->{ dragHelper.capturedView!!.elevation --}
            }
        }

        /**\
         * capturedView ?????????????????????
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
         * ???captureview??????????????????
         */
        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            capturedChild.elevation += 1
            captureviewLeft = capturedChild.left
            captureviewTop = capturedChild.top
        }

        /**
         * ??????????????????????????????
         */
        override fun onEdgeTouched(edgeFlags: Int, pointerId: Int) {
            super.onEdgeTouched(edgeFlags, pointerId)
        }

        /**
         * ????????????????????????
         * ????????????dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)
         */
        override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
            super.onEdgeDragStarted(edgeFlags, pointerId)
        }

        /**
         * ???????????????????????????
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