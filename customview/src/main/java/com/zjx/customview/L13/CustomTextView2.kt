package com.zjx.customview.L13

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.zjx.customview.R
import com.zjx.customview.getBitmap

class CustomTextView2(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val picWidth = 300
    private val picTopMargin = 100f
    private val textSie = 50f
    private val bitmap = getBitmap(resources, R.mipmap.xiaoxin, picWidth)
    private val text = "新京报快讯 据国家卫健委消息，10月24日0—24时，31个省(自治区、直辖市)和新疆生产建设兵团报告新增确诊病例39例。其中境外输入病例4例(天津1例，上海1例，山东1例，广西1例);本土病例35例(内蒙古19例，其中阿拉善盟18例、锡林郭勒盟1例;贵州4例，均在遵义市;甘肃4例，其中兰州市3例、嘉峪关市1例;北京2例，其中丰台区1例、海淀区1例;河北2例，均在邢台市;湖南2例，均在长沙市;陕西2例，均在西安市)，含5例由无症状感染者转为确诊病例(河北2例，湖南2例，贵州1例)。无新增死亡病例。新增疑似病例1例，为境外输入病例(在内蒙古)。"
    private val measureWidth  = floatArrayOf()

    init {
        paint.strokeWidth = 10f
        paint.textSize = textSie
        paint.textAlign = Paint.Align.LEFT
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, (width-picWidth).toFloat(), picTopMargin, paint)

        var oldIndex = 0
        var lineCount = 0
        var index = 0
        while (oldIndex < text.length) {
            if (textSie+paint.fontSpacing*lineCount > picTopMargin && textSie+paint.fontSpacing*(lineCount-1) < picTopMargin+picWidth) {
                index = paint.breakText(text, oldIndex, text.length, true, (width-picWidth).toFloat(), measureWidth)
            } else {
                index = paint.breakText(text, oldIndex, text.length, true, width.toFloat(), measureWidth)
            }
            canvas.drawText(text, oldIndex, oldIndex+index, 0f, textSie + paint.fontSpacing*lineCount, paint)
            lineCount++
            oldIndex += index
        }
    }
}