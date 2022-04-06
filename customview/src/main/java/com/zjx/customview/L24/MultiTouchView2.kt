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

class MultiTouchView2(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmapWidth = 300f.dp
    private val bitmap = getBitmap(resources, R.mipmap.xiaoxin, bitmapWidth.toInt())
    private var offsetX = 0f
    private var offsetY = 0f
    private var originOffsetX = 0f
    private var originOffsetY = 0f
    private var downX = 0f
    private var downY = 0f

    override fun onDraw(canvas: Canvas) {
        canvas.translate(offsetX, offsetY)
        canvas.drawBitmap(bitmap, (width-bitmapWidth)/2, (height-bitmapWidth)/2, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var focusX = 0f
        var focusY = 0f
        var pointerCount = event.pointerCount
        var sumX = 0f
        var sumY = 0f
        val isPointerup = event.actionMasked == MotionEvent.ACTION_POINTER_UP
        for(i in 0 until pointerCount) {
            if (!(isPointerup && i == event.actionIndex)) {
                sumX += event.getX(i)
                sumY += event.getY(i)
            }
        }
        if (isPointerup) pointerCount--
        focusX = sumX/pointerCount
        focusY = sumY/pointerCount
        when(event.actionMasked) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_POINTER_DOWN,
            MotionEvent.ACTION_POINTER_UP->{
                originOffsetX = offsetX
                originOffsetY = offsetY
                downX = focusX
                downY = focusY
            }
            MotionEvent.ACTION_MOVE->{
                offsetX = focusX-downX+originOffsetX
                offsetY = focusY-downY+originOffsetY
                invalidate()
            }
        }
        return true
    }
}