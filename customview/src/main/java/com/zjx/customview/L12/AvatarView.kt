package com.zjx.customview.L12

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.zjx.customview.R
import com.zjx.customview.getBitmap

class AvatarView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val picWidth = 600
    private val padding = 100f
    private val strokeWidth = 15f
    private var saveRectF: RectF = RectF(padding, padding, padding+picWidth, padding+picWidth)
    private var bitmap: Bitmap = getBitmap(resources, R.mipmap.xiaoxin, picWidth)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        //先绘制一个底图，就是一个圆形的黑色背景（头像上周围的黑色圆形框）
        canvas.drawOval(saveRectF, paint)
        //进行离屏缓冲，将一块区域单独拿出来进行操作
        val saveLayer = canvas.saveLayer(saveRectF, paint)
        //先绘制一个图形
        canvas.drawOval(padding+strokeWidth, padding+strokeWidth, padding+picWidth-strokeWidth, padding+picWidth-strokeWidth, paint)
        //设置xfermode
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        //再绘制一个图形，这个图形就会按照设置的xfermode类型来根据上一个图形来进行裁剪
        canvas.drawBitmap(bitmap, padding, padding, paint)
        paint.xfermode = null
        //将离屏缓冲的区域重新放到绘制区域
        canvas.restoreToCount(saveLayer)
    }

}