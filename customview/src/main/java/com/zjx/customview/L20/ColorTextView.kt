package com.zjx.customview.L20

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.zjx.customview.dp

class ColorTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textColors = listOf<Int>(Color.parseColor("#3399FF"),
        Color.parseColor("#FF3300"),
        Color.parseColor("#99FF33"),
        Color.parseColor("#FF9900"),
        Color.parseColor("#FFFF33"),
        Color.parseColor("#FF99FF"),
        Color.parseColor("#9999FF"))
    private val textSizes = listOf<Float>(16f, 32f, 40f)
    private val xPadding = 16f.dp
    private val yPadding = 8f.dp
    private val cornerRadius = 4f.dp

    init {
        setTextColor(Color.WHITE)
        paint.color = textColors[(0 until textColors.size).random()]
        textSize = textSizes[(0 until textSizes.size).random()]
        setPadding(xPadding.toInt(), yPadding.toInt(), xPadding.toInt(), yPadding.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), cornerRadius,cornerRadius, paint)
        super.onDraw(canvas)
    }
}