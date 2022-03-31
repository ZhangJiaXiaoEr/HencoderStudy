package com.zjx.customview.L13

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.zjx.customview.R
import com.zjx.customview.getBitmap

class CustomTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val radius = 300f
    private lateinit var rectF: RectF
    private var bitmap: Bitmap = getBitmap(resources, R.mipmap.xiaoxin, radius.toInt())
    private val text = "新京报快讯 据国家卫健委消息，10月24日0—24时，31个省(自治区、直辖市)和新疆生产建设兵团报告新增确诊病例39例。其中境外输入病例4例(天津1例，上海1例，山东1例，广西1例);本土病例35例(内蒙古19例，其中阿拉善盟18例、锡林郭勒盟1例;贵州4例，均在遵义市;甘肃4例，其中兰州市3例、嘉峪关市1例;北京2例，其中丰台区1例、海淀区1例;河北2例，均在邢台市;湖南2例，均在长沙市;陕西2例，均在西安市)，含5例由无症状感染者转为确诊病例(河北2例，湖南2例，贵州1例)。无新增死亡病例。新增疑似病例1例，为境外输入病例(在内蒙古)。"
    private val picWidth = 400
    private val measuredWidth = floatArrayOf()

    init {

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF = RectF(width/2 - radius, height/2 - radius, width/2 + radius, height/2 + radius)
    }

    override fun onDraw(canvas: Canvas) {
        paint.strokeWidth = 30f
        paint.style = Paint.Style.STROKE
        //绘制一个圆环
        paint.color = Color.parseColor("#C0C0C0")
        canvas.drawArc(rectF, 0f, 360f, false, paint)

        //绘制一个圆角的半环
        paint.color = Color.parseColor("#FF3366")
        paint.strokeCap = Paint.Cap.ROUND//设置线头是圆角的
        canvas.drawArc(rectF, 180f, 250f, false, paint)

        paint.strokeWidth = 10f
        paint.style = Paint.Style.FILL
        //绘制中间的文字
        paint.textSize = 150f
        paint.textAlign = Paint.Align.CENTER//设置文字居中

        //设置文字上下居中
        //方法一：（这种方式在文字是动态变化的时候会导致文字上下跳动）
//        val bounds = Rect()
//        paint.getTextBounds("abcd", 0, "abcd".length, bounds)
//        val offset = (bounds.top + bounds.bottom) / 2
//        canvas.drawText("abcd", (width/2).toFloat(), (height/2 - offset).toFloat(), paint)

        //方法二:
        val fontMeasure = Paint.FontMetrics()
        paint.getFontMetrics(fontMeasure)
        val offset = (fontMeasure.ascent + fontMeasure.descent) / 2
        canvas.drawText("abcd", (width/2).toFloat(), (height/2 - offset).toFloat(), paint)
    }

}