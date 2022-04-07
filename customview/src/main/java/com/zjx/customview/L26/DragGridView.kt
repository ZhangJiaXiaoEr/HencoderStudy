package com.zjx.customview.L26

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children

class DragGridView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private val columns = 2
    private val rows = 3
    private var dragView: View? = null
    private val orderChidren: MutableList<View> = ArrayList()
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
        var childLeft: Int
        var childTop: Int
        val childWidth = width/columns
        val childHeight = height/rows
        for ((index, child) in children.withIndex()) {
            childLeft = index%columns * childWidth
            childTop = index/columns * childHeight
            child.layout(0, 0, childWidth, childHeight)
            child.translationX = childLeft.toFloat()
            child.translationY = childTop.toFloat()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        for ((index, child) in children.withIndex()){
            orderChidren.add(child)
            child.setOnLongClickListener {
                dragView = it
                it.startDrag(null, DragShadowBuilder(it), it, 0)
                false
            }
            child.setOnDragListener { v, event ->
                when(event.action) {
                    DragEvent.ACTION_DRAG_STARTED->if (v === event.localState) v.visibility = INVISIBLE
                    DragEvent.ACTION_DRAG_ENTERED->if (v !== event.localState){
                        sort(v)
                    }
                    DragEvent.ACTION_DRAG_ENDED->if (v === event.localState) v.visibility = VISIBLE
                }
                true
            }
        }
    }

    private fun sort(view: View) {
        var dragIndex = -1
        var targetIndex = -1
        for ((index, child) in orderChidren.withIndex()) {
            if (child === view) {
                targetIndex = index
            } else if (child === dragView) {
                dragIndex = index
            }
        }
        orderChidren.removeAt(dragIndex)
        orderChidren.add(targetIndex, dragView!!)

        var childLeft: Int
        var childTop: Int
        val childWidth = width/columns
        val childHeight = height/rows
        for ((index, child) in orderChidren.withIndex()) {
            childLeft = index%columns * childWidth
            childTop = index/columns * childHeight
            child.animate()
                .translationX(childLeft.toFloat())
                .translationY(childTop.toFloat())
                .setDuration(150)

        }
    }
}