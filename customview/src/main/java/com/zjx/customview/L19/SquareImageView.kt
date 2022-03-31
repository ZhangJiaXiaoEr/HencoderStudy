package com.zjx.customview.L19

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

class SquareImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    /**
     * view在这个方法中计算出自己的期望尺寸， 然后进行保存
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        /*//获取到测量中的宽高
        getMeasuredWidth()
        getMeasuredHeight()
        //获取到的是最后的宽高
        getWidth()
        getHeight()*/
        val size = min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }
}