package com.zjx.customview.L23

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.zjx.customview.R
import com.zjx.customview.dp
import com.zjx.customview.getBitmap
import kotlin.math.max
import kotlin.math.min

class ScaleableImageView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var originalOffsetX = 0f//canvas初始的X偏移
    private var originalOffsetY = 0f//canvas初始的Y偏移
    var offsetX = 0f//X的偏移量
    var offsetY = 0f//Y的偏移量
    private var smallScale = 0f//最大的放大倍数
    private var bigScale = 0f//
    private var currentScale = 1f
    private val bitmapWidth = 200f.dp
    private val bitmap = getBitmap(resources, R.mipmap.xiaoxin, bitmapWidth.toInt())
    private val gestureDetector = GestureDetectorCompat(context, MyOnGestureListener())//滑动的手势
    private val scaleGestureDetector = ScaleGestureDetector(context, MyOnScaleGestureListener())//缩放的手势

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (width - bitmapWidth)/2
        originalOffsetY = (height - bitmapWidth)/2

        if (bitmapWidth/width > bitmapWidth/height) {
            smallScale = width/bitmapWidth
            bigScale = smallScale * 3
        } else {
            smallScale = height/bitmapWidth
            bigScale = smallScale * 3
        }
    }

    override fun onDraw(canvas: Canvas) {
        //倒着进行写代码
        //第二次偏移
        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX*scaleFraction, offsetY*scaleFraction)
        //进行缩放
        canvas.scale(currentScale ,currentScale, (width/2).toFloat(), (height/2).toFloat())
        //进行第一次偏移
        canvas.translate(originalOffsetX, originalOffsetY)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var result = scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) {
            result = gestureDetector.onTouchEvent(event)
        }
        return result
    }

    private inner class MyOnScaleGestureListener: ScaleGestureDetector.OnScaleGestureListener {
        private var originalScale = 1f
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            currentScale = originalScale * detector.scaleFactor
            currentScale = max(currentScale, 1f)
            currentScale = min(currentScale, bigScale)

            invalidate()
            return false
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            originalScale = currentScale
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
        }

    }

    /**
     *
     * kotlin内部类需要加inner关键字修饰
     */
    private inner class MyOnGestureListener: GestureDetector.OnGestureListener {
        //每次ACTION_DOWN事件都会被调用，这里返回true可以保证事件被消费
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }
        //用户按下100ms后会被调用，用于标记【可以显示按下状态】
        override fun onShowPress(e: MotionEvent?) {
        }
        //用户单击时被调用【长按抬起，或者双击的第二下不会被调用】
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return false
        }

        /**
         * 用户滑动时调用
         * downEvent  down事件
         * currentEvent  当前的事件
         * distanceX 滑动的X距离
         * distanceY 滑动的Y距离
         */
        override fun onScroll(
            downEvent: MotionEvent?,
            currentEvent: MotionEvent?,
            distanceX: Float,
            distanceY: Float,
        ): Boolean {
            scroll(distanceX, distanceY)
            return false
        }
        //用户长按（按下超过500ms）会被调用
        override fun onLongPress(e: MotionEvent?) {
        }
        //用户滑动迅速抬起时调用，用于惯性滑动效果
        override fun onFling(
            downEvent: MotionEvent?,
            currentEvent: MotionEvent?,
            velocityX: Float,
            velocityY: Float,
        ): Boolean {
            return false
        }

    }
    private fun scroll(distanceX: Float, distanceY: Float) {
        if (currentScale > smallScale) {
            if (bitmapWidth*currentScale > width) {
                offsetX -= distanceX
                offsetX = max(offsetX, -(bitmapWidth*currentScale - width)/2)
                offsetX = min(offsetX, (bitmapWidth*currentScale - width)/2)
            }
            if (bitmapWidth*currentScale > height) {
                offsetY -= distanceY
                offsetY = max(offsetY, -(bitmapWidth*currentScale - height)/2)
                offsetY = min(offsetY, (bitmapWidth*currentScale - height)/2)
            }
            invalidate()
        }
    }
}