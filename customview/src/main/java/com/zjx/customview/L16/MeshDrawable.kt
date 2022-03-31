package com.zjx.customview.L16

import android.graphics.*
import android.graphics.drawable.Drawable

class MeshDrawable: Drawable() {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        strokeWidth = 10f
    }
    private val JIANGE = 60
    override fun draw(canvas: Canvas) {
        var x = bounds.left
        while (x <= bounds.right) {
            canvas.drawLine(x.toFloat(), bounds.top.toFloat(), x.toFloat(), bounds.bottom.toFloat(), paint)
            x += JIANGE
        }

        var y = bounds.top
        while (y <= bounds.bottom) {
            canvas.drawLine(bounds.left.toFloat(), y.toFloat(), bounds.right.toFloat(), y.toFloat(), paint)
            y += JIANGE
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getAlpha(): Int {
        return paint.alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getColorFilter(): ColorFilter? {
        return paint.colorFilter
    }

    /**
     * 返回不透明度，根据透明度来定义
     */
    override fun getOpacity(): Int {
        return when(paint.alpha) {
            0 -> PixelFormat.TRANSPARENT
            0xff -> PixelFormat.OPAQUE
            else -> PixelFormat.TRANSLUCENT
        }
    }
}