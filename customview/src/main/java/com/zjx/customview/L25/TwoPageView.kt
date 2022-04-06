package com.zjx.customview.L25

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.*
import android.widget.OverScroller
import kotlin.math.abs

class TwoPageView(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private var downX = 0f
    private var downY = 0f
    private var downScrollX = 0
    private val scroller: OverScroller = OverScroller(context)
    private val velocityTracker = VelocityTracker.obtain()//测速器
    private val viewConfiguration = ViewConfiguration.get(context)
    private val minVelocity = viewConfiguration.scaledMinimumFlingVelocity//最小的滑动速度
    private val maxVelocity = viewConfiguration.scaledMaximumFlingVelocity//最大的滑动速度
    private val pagingSlop = viewConfiguration.scaledPagingTouchSlop//是否拦截滑动事件的最小滑动距离
    private var scrolling = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //测量子view，默认子view为matchparent
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
    override fun onLayout(change: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var childLeft = 0
        var childeRight = width
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.layout(childLeft, top, childeRight, bottom)
            childLeft += width
            childeRight += height
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            //down事件时清空测速器
            velocityTracker.clear()
        }
        //往测速器中添加事件，为了计算滑动速度
        velocityTracker.addMovement(event)
        var result = false
        when(event.actionMasked) {
            MotionEvent.ACTION_DOWN->{//down事件时逻辑与onToucheEvent一样，保证能记录下down事件的各种状态
                scrolling = false
                downX = event.x
                downY = event.y
                downScrollX = scrollX
            }
            MotionEvent.ACTION_MOVE->if (!scrolling){
                val dx = downX - event.x
                if (abs(dx) > pagingSlop) {//当滑动的距离大于pagingSlop时进行拦截事件，并防止父view拦截事件
                    scrolling = true
                    result = true
                    parent.requestDisallowInterceptTouchEvent(true)
                }
            }
        }
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        when(event.actionMasked) {
            MotionEvent.ACTION_DOWN->{
                downX = event.x
                downY = event.y
                downScrollX = scrollX
            }
            MotionEvent.ACTION_MOVE->{
                //downScrollX为上一个事件序列中X滑动的距离
                val dx = (downX - event.x + downScrollX).toInt()
                    .coerceAtLeast(0)//设置滑动的最小值
                    .coerceAtMost(width)//设置滑动的最大值
                scrollTo(dx, 0)
            }
            MotionEvent.ACTION_UP->{
                //计算手指抬起时的滑动速度，并设置一个最大的滑动速度
                velocityTracker.computeCurrentVelocity(1000, maxVelocity.toFloat())
                //获取到手指抬起时的滑动速度
                val vx = velocityTracker.xVelocity
                val scrollX = scrollX
                val targetPage = if (abs(vx) < minVelocity) {//慢速滑动
                    //如果滑动的距离大于VieWGroup的一半，就说明要滑到第二个子View
                    if (scrollX > width/2) 1 else 0
                } else {//滑动速度快于某一个值
                    //如果滑动速度小于0（就是向左滑）说明用户想滑到第二个子View
                    if (vx < 0) 1 else 0
                }
                //计算松手后需要滑动的距离
                val scrollDistance = if (targetPage == 1) width-scrollX else -scrollX
                scroller.startScroll(getScrollX(), 0, scrollDistance, 0)
                postInvalidateOnAnimation()
            }
        }
        println("是否滑动：$scrolling")
        return true
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            postInvalidateOnAnimation()
        }
    }

}