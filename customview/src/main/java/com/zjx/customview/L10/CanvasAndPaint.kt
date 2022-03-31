package com.zjx.customview.L10

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CanvasAndPaint(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    lateinit var paint: Paint

    init {
        //Paint.ANTI_ALIAS_FLAG:抗锯齿，边界更平滑
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.isAntiAlias = true//也是抗锯齿
    }

    override fun onDraw(canvas: Canvas?) {
        paint.color = Color.BLACK
        //实心的还是只画边
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5.0f

        //画一个圆
//        canvas?.drawCircle(100.0f, 100.0f, 50.0f, paint)

        //画一个矩形
//        canvas?.drawRect(RectF(50.0f, 50.0f, 150.0f, 100.0f), paint)
//        canvas?.drawRect(Rect(50, 50, 150, 100), paint)

        //绘制椭圆
//        canvas?.drawOval(RectF(50.0f, 50.0f, 250.0f, 150.0f), paint)

        //画圆角矩形
//        canvas?.drawRoundRect(RectF(50.0f, 50.0f, 250.0f, 150.0f), 30.0f, 30.0f, paint)

        //画弧形或者扇形, startAngle从最右边开始为0
        canvas?.drawArc(RectF(50.0f, 50.0f, 250.0f, 250.0f), 0.0f, 120.0f, true, paint)
    }
}