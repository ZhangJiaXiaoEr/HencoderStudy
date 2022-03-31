package com.zjx.customview.L11

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

/**
 * 饼形图
 */
class PieChat(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var arcRectF: RectF
    private val radius: Float = 300f
    private var currentAngle = 0f
    private var angleAdvance: Array<Float> = arrayOf(60f, 100f, 120f, 80f)
    private val colorArray: Array<String> = arrayOf("#33CCCC", "#FF6600", "#0066CC", "#339933")

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        arcRectF = RectF(width/2-radius, height/2-radius, width/2+radius, height/2+radius)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        angleAdvance.forEachIndexed { index, angle ->
            val color = colorArray[index]
            paint.color = Color.parseColor(color)
//            if ("#0066CC"==color) {
//                val translateX = cos(Math.toRadians((currentAngle+angle/2).toDouble())) * 15
//                val translateY = sin(Math.toRadians((currentAngle+angle/2).toDouble())) * 15
//                val arcRectF1 = RectF((width/2-radius+translateX).toFloat(),
//                    (height/2-radius+translateY).toFloat(),
//                    (width/2+radius+translateX).toFloat(),
//                    (height/2+radius+translateY).toFloat())
//                canvas.drawArc(arcRectF1, currentAngle, angle, true, paint)
//            } else {
//                canvas.drawArc(arcRectF, currentAngle, angle, true, paint)
//            }

            canvas.save()
            if ("#FF6600"==color) {
                val translateX = cos(Math.toRadians((currentAngle+angle/2).toDouble())) * 15
                val translateY = sin(Math.toRadians((currentAngle+angle/2).toDouble())) * 15
                canvas.translate(translateX.toFloat(), translateY.toFloat())
            }
            canvas.drawArc(arcRectF, currentAngle, angle, true, paint)
            canvas.restore()
            currentAngle += angle
        }

    }
}