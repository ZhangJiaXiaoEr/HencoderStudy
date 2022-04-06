package com.zjx.customview.L23

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.zjx.customview.R
import com.zjx.customview.dp
import com.zjx.customview.getBitmap
import kotlin.math.max
import kotlin.math.min

class ScaleableImageView(context: Context, attrs: AttributeSet?) : View(context, attrs), Runnable {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var originalOffsetX = 0f//canvas初始的X偏移
    private var originalOffsetY = 0f//canvas初始的Y偏移
    var offsetX = 0f//X的偏移量
    var offsetY = 0f//Y的偏移量
    private var smallScale = 0f//最大的放大倍数
    private var bigScale = 0f//
    private var currentScale = 1f
        get
        set(value) {
            field = value
            invalidate()
        }
    private var bitmapWidth = 200f.dp
    private val flipOver = 150
    private val bitmap : Bitmap = getBitmap(resources, R.mipmap.xiaoxin, bitmapWidth.toInt())
    private val gestureDetector = GestureDetectorCompat(context, MyOnGestureListener())//滑动的手势
    private val scaleGestureDetector = ScaleGestureDetector(context, MyOnScaleGestureListener())//缩放的手势
    private val scroller: OverScroller = OverScroller(context)
    private lateinit var animator: ObjectAnimator
    private fun animatorIsInitialized() =:: animator.isInitialized

    init {
        gestureDetector.setOnDoubleTapListener(MyOnDoubleTapListener())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (bitmapWidth/width > bitmapWidth/height) {
            smallScale = width/bitmapWidth
            bigScale = smallScale * 3
        } else {
            smallScale = height/bitmapWidth
            bigScale = smallScale * 3
        }
        originalOffsetY = (height - bitmapWidth)/2
        originalOffsetX = (width - bitmapWidth)/2
        currentScale = smallScale
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

    @SuppressLint("ClickableViewAccessibility")
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
            currentScale = max(currentScale, smallScale)
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
            flip(velocityX, velocityY)
            return false
        }

    }

    /**
     * 手指抬起之后进行惯性滑动
     */
    private fun flip(velocityX: Float,
                     velocityY: Float) {
        if (currentScale > smallScale) {
            //初始化滑动
            scroller.fling(offsetX.toInt(), offsetY.toInt(),
                velocityX.toInt(), velocityY.toInt(),
                (-(bitmapWidth*currentScale - width)/2).toInt(),
                ((bitmapWidth*currentScale - width)/2).toInt(),
                (-(bitmapWidth*currentScale - height)/2).toInt(),
                ((bitmapWidth*currentScale - height)/2).toInt(),
                flipOver, flipOver)
            //下一帧刷新
            ViewCompat.postOnAnimation(this, this)
        }
    }

    /**
     * 手指在屏幕上时滑动
     */
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

    private inner class MyOnDoubleTapListener: GestureDetector.OnDoubleTapListener{
        //用户单击时调用，需要在确定用户300ms内没有点击第二次时才会调用
        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            return false
        }

        //用户双击，第二次按下时调用
        override fun onDoubleTap(e: MotionEvent): Boolean {
            if (currentScale < bigScale) {
                offsetX = (width/2 - e.x)*(bigScale-currentScale)
                    .coerceAtLeast(-(bitmapWidth*bigScale - width)/2)
                    .coerceAtMost((bitmapWidth*bigScale - width)/2)
                offsetY = (height/2 - e.y)*(bigScale-currentScale)
                    .coerceAtLeast(-(bitmapWidth*bigScale - height)/2)
                    .coerceAtMost((bitmapWidth*bigScale - height)/2)
                getAnimator(currentScale, bigScale).start()
            } else {
                getAnimator(bigScale, smallScale).start()
            }
            return false
        }

        //用户双击， 第二次按下、拖动、抬起都会调用
        //用于双击拖拽
        override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
            return false
        }

    }

    /**
     *
     */
    private fun getAnimator(startValue: Float, endValue: Float): ObjectAnimator {
        if (!animatorIsInitialized()) {
            animator = ObjectAnimator.ofFloat(this@ScaleableImageView, "currentScale", 0f, 1f)
            animator.addListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    if (this@ScaleableImageView.currentScale == this@ScaleableImageView.smallScale) {
                        offsetX = 0f
                        offsetY = 0f
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

            })
        }
        animator.setFloatValues(startValue, endValue)
        return animator
    }

    override fun run() {
        //计算此时的位置，如果滑动结束就停止
        if (scroller.computeScrollOffset()) {
            //将此时的位置应用于界面
            offsetX = scroller.currX.toFloat()
            offsetY = scroller.currY.toFloat()
            invalidate()
            //下一帧刷新
            ViewCompat.postOnAnimation(this, this)
        }
    }
}
