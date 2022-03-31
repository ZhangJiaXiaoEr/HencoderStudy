package com.zjx.customview.L19

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.zjx.customview.dp
import kotlin.math.min

class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val RADIUS = 100f.dp
    private val PADDING = 100f.dp

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
       /* val widthSpec = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val width = when(widthSpec) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> min(measuredWidth, widthSize)
            else -> measuredWidth
        }
        val heightSpec = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val height = when(heightSpec) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(measuredHeight, heightSize)
            else -> measuredHeight
        }*/

        val width = resolveSize(measuredWidth, widthMeasureSpec)
        val height = resolveSize(measuredHeight, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(RADIUS+PADDING, RADIUS+PADDING, RADIUS, paint)
    }

}