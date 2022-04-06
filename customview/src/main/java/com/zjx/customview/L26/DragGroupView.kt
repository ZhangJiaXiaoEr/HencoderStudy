package com.zjx.customview.L26

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children

class DragGroupView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private val columns = 2
    private val rows = 3
    private var dragView: View? = null
    private var orderdChildren: MutableList<View> = ArrayList()

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
            childTop = index/columns * childHeight
            child.layout(0, 0, childWidth, childHeight)
            child.translationX = childLeft.toFloat()
            child.translationY = childTop.toFloat()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        for ((index, child) in children.withIndex()){
            child.setTag(index)
            orderdChildren.add(child)
            child.setOnLongClickListener {
                dragView = it
                it.startDrag(null, DragShadowBuilder(it), it, 0)
                false
            }

            child.setOnDragListener(MyOnDragListener())
        }
    }

    inner class MyOnDragListener: OnDragListener{
        override fun onDrag(view: View, event: DragEvent): Boolean {
            when(event.action) {
                //拖动开始
                DragEvent.ACTION_DRAG_STARTED->{
                    if (view === event.localState) {
                        view.visibility = View.INVISIBLE
                    }
                }
                //表示有drag的view进入到当前view的区域
                DragEvent.ACTION_DRAG_ENTERED->{
                    if (view !== event.localState) {
                        sort(view)
                    }
                }
                //表示drag的view在当前的view的区域中，改变了它的位置
                DragEvent.ACTION_DRAG_LOCATION->{}
                //表示drag的view离开了当前的view的区域
                DragEvent.ACTION_DRAG_EXITED->{}
                //真正的drop操作，就是你的drag动作松开手指
                DragEvent.ACTION_DROP->{}
                //drag和drop操作都结束。
                DragEvent.ACTION_DRAG_ENDED->{
                    if (view === event.localState) {
                        view.visibility = View.VISIBLE
                    }
                }
            }
            return true
        }
    }

    private fun sort(view: View) {
        var targetIndex = 0
        var dragIndex = 0
        for ((index, child) in orderdChildren.withIndex()) {
            if (child === view) {
                targetIndex = index
            }else if (view === dragView) {
                dragIndex = index
            }
        }
        orderdChildren.removeAt(dragIndex)
        orderdChildren.add(targetIndex, dragView!!)
        var childLeft = 0
        var childTop = 0
        val childWidth = width/columns
        val childHeight = height/rows
        for ((index, child) in orderdChildren.withIndex()) {
            childLeft = index%columns * childWidth
            childTop = index/columns * childHeight
            Log.e("DragGroup", "dragIndex=$dragIndex, targetIndex=$targetIndex, childLeft=$childLeft, childeTop=$childTop, tag=${child.tag}")
            child.animate()
                .setDuration(150)
                .translationX(childLeft.toFloat())
                .translationY(childTop.toFloat())
        }
    }
}