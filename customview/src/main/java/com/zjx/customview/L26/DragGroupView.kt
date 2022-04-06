package com.zjx.customview.L26

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children

class DragGroupView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private val columns = 2
    private val rows = 3
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = specWidth/columns
        val childHeight = specHeight/rows
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY))
        setMeasuredDimension(specWidth, specHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        var childTop = 0
        val childWidth = width/columns
        val childHeight = height/rows
        for ((index, child) in children.withIndex()) {
            childLeft = index%columns * childWidth
            childTop = index/rows * childHeight
            child.layout(childLeft, childTop, childLeft+childWidth, childTop+childHeight)
        }
    }
}