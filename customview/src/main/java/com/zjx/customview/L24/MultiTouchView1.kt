package com.zjx.customview.L24

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.zjx.customview.R
import com.zjx.customview.dp
import com.zjx.customview.getBitmap
import kotlin.math.max
import kotlin.math.min

class MultiTouchView1(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmapWidth = 300f.dp
    private val bitmap = getBitmap(resources, R.mipmap.xiaoxin, bitmapWidth.toInt())
    private var offsetX = 0f
    private var offsetY = 0f
    private var originOffsetX = 0f
    private var originOffsetY = 0f
    private var downX = 0f
    private var downY = 0f
    private var currentPointerId: Int = 0//当前起滑动作用手指的id

    override fun onDraw(canvas: Canvas) {
        canvas.translate(offsetX, offsetY)
        canvas.drawBitmap(bitmap, (width-bitmapWidth)/2, (height-bitmapWidth)/2, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var isConsume = false
        when(event.actionMasked){
            MotionEvent.ACTION_DOWN->{
                originOffsetX = offsetX
                originOffsetY = offsetY
                if (event.x > ((width-bitmapWidth)/2+originOffsetX)
                    && event.x < ((width+bitmapWidth)/2+originOffsetX)
                    && event.y > ((height-bitmapWidth)/2+originOffsetY)
                    && event.y < ((height+bitmapWidth)/2+originOffsetY)){
                    currentPointerId = event.getPointerId(0)
                    isConsume = true
                }
                downX = event.x
                downY = event.y
            }
            MotionEvent.ACTION_MOVE->{
                //根据pointerId获取对应手指的index
                val index = event.findPointerIndex(currentPointerId)
                //根据index获取具体x和y轴的滑动
                offsetX = event.getX(index) - downX + originOffsetX
                offsetY = event.getY(index) - downY + originOffsetY
                //图片不能划出界面，进行限定
                offsetX = min(offsetX, (width-bitmapWidth)/2)
                offsetX = max(offsetX, -(width-bitmapWidth)/2)
                offsetY = min(offsetY, (height-bitmapWidth)/2)
                offsetY = max(offsetY, -(height-bitmapWidth)/2)
                invalidate()
            }
            MotionEvent.ACTION_POINTER_DOWN->{//不是第一根手指按下时调用
                //我们去最后一根手指作为起作用的手指
                //获取按下手指的index，并获取到其id，更新downX和downY
                val index = event.actionIndex
                currentPointerId = event.getPointerId(index)
                originOffsetX = offsetX
                originOffsetY = offsetY
                downX = event.getX(index)
                downY = event.getY(index)
            }
            MotionEvent.ACTION_POINTER_UP->{//不是最后一根手指时调用
                //判断抬起的是否是起作用的手指
                if (currentPointerId == event.getPointerId(event.actionIndex)) {
                    //如果抬起的是最后一根手指，就取倒数第二根手指是起作用，否则就取最后一根
                    val index = if (event.actionIndex == event.pointerCount-1) {
                        event.pointerCount-2
                    } else {
                        event.pointerCount-1
                    }
                    currentPointerId = event.getPointerId(index)
                    originOffsetX = offsetX
                    originOffsetY = offsetY
                    downX = event.getX(index)
                    downY = event.getY(index)
                }
            }
        }
        return isConsume
    }
}