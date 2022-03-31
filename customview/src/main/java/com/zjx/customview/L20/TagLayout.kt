package com.zjx.customview.L20

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.*
import java.util.ArrayList
import kotlin.math.max

class TagLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {
    val childrenBounds = ArrayList<Rect>()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        var lineWidthUsed = 0//一行已被用掉的宽度
        var lineMaxHeight = 0//一行的最大高度
        var layoutWidth = 0//本layout的宽度
        var layoutHeight = 0//本layout的高度
        children.forEachIndexed { index, child ->
            //测量子View的期望宽高，加上margin的
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
            if (specWidthMode != MeasureSpec.UNSPECIFIED &&
                lineWidthUsed+child.measuredWidth > specWidthSize) {//剩余宽度不足以容纳子View，另起一行
                layoutHeight += lineMaxHeight
                lineMaxHeight = 0
                lineWidthUsed = 0
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
            }
            if (childrenBounds.size <= index) childrenBounds.add(Rect())
            childrenBounds[index].set(lineWidthUsed,
                    layoutHeight,
                lineWidthUsed + child.measuredWidth,
                layoutHeight + child.measuredHeight)
            lineWidthUsed += child.measuredWidth
            lineMaxHeight = max(lineMaxHeight, child.measuredHeight)
            layoutWidth = max(layoutWidth, lineWidthUsed)
        }
        val selfWidth = layoutWidth
        val selfHeight = layoutHeight + lineMaxHeight
        setMeasuredDimension(selfWidth, selfHeight)
    }

    /**
     * 调用measureChildWithMargins方法，必须要重写这个方法，返回MarginLayoutParams
     */
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        children.forEachIndexed { index, child ->
            val childBound = childrenBounds[index]
            child.layout(childBound.left, childBound.top, childBound.right, childBound.bottom)
        }
    }
}