package com.zjx.customview.L16

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class DrawableView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        val drawable = MeshDrawable()
        drawable.bounds = Rect(30, 60, 230, 260)
        drawable.draw(canvas)
    }
}