package com.zjx.customview.L11

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

/**
 * 表盘
 */
class DashBoard(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val angle = 120f
    private val radius = 300f
    private val pointerLength = 200f
    private var dash: Path
    private var effect: PathDashPathEffect
    private var arc: Path

    init {
        //Paint.ANTI_ALIAS_FLAG:抗锯齿，边界更平滑
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10.0f
        paint.isAntiAlias = true//也是抗锯齿

        //这个是刻度，就是画一个个的小长方形
        dash = Path()
        dash.addRect(RectF(0.0f, 0.0f, 4f, 16f), Path.Direction.CW)

        //
        //计算arc的长度
        arc = Path()
        arc.addArc(RectF(width/2-radius, height/2-radius, width/2+radius, height/2+radius), 90+angle/2, 360-angle)
        val pathMeasure = PathMeasure(arc, false)
        //paint添加了PathDashPathEffect之后， 就只会按照PathDashPathEffect的路径话dash，而不会画圆弧
        effect = PathDashPathEffect(dash, (pathMeasure.length - 4)/20, 0f,  PathDashPathEffect.Style.ROTATE)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val centerX = width / 2
        val centerY = height / 2
        val arcRectf = RectF(centerX-radius, centerY-radius, centerX+radius, centerY+radius)
        //先画一个表盘
        canvas.drawArc(arcRectf, 90+angle/2, 360-angle, false, paint)

        //画刻度
        paint.pathEffect = effect
        canvas.drawArc(arcRectf, 90+angle/2, 360-angle, false, paint)
        paint.pathEffect = null

        //画指针
        canvas.drawLine((width/2).toFloat(), (height/2).toFloat(),
            (cos(Math.toRadians(getAngleForMark(7))) *pointerLength + width/2).toFloat(),
            (sin(Math.toRadians(getAngleForMark(7))) *pointerLength + height/2).toFloat(),
        paint)
    }

    /**
     * 计算出指针和x轴正轴的夹角
     */
    private fun getAngleForMark(mark: Int): Double{
        return 90 + (angle/2).toDouble() + ((360-angle)/20*mark).toDouble()
    }
}