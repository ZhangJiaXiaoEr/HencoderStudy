package com.zjx.customview.MyView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap
import com.zjx.customview.R
import com.zjx.customview.dp
import kotlin.math.abs

@SuppressLint("ResourceType")
class BubbleImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {
    private val bitmapPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val LOCATION_LEFT = 0 //箭头方向左
    private var COLORDRAWABLE_DIMENSION = 1
    private var arrowLocation = COLORDRAWABLE_DIMENSION//箭头的方便 1---左边
    private var arrowTop = 40f.dp//箭头距离顶部的距离
    private var arrowWidth = 20f.dp//箭头的宽度
    private var arrowHeight = 20f.dp//箭头的高度
    private var angle  = 7f.dp//圆角的半径
    private var bitmap: Bitmap? = null
    private var bitmapShader: BitmapShader? = null

    init {
        val obtainStyledAttributes =
            context.obtainStyledAttributes(attrs, R.styleable.BubbleImageView)
//        angle = obtainStyledAttributes.getDimension(R.attr.bubble_angle, angle)
//        arrowLocation = obtainStyledAttributes.getInteger(R.attr.bubble_arrowLocation, arrowLocation)
//        arrowTop = obtainStyledAttributes.getDimension(R.attr.bubble_arrowTop, arrowTop)
//        arrowWidth = obtainStyledAttributes.getDimension(R.attr.bubble_arrowWidth, arrowWidth)
//        arrowHeight = obtainStyledAttributes.getDimension(R.attr.bubble_arrowHeight, arrowHeight)
        obtainStyledAttributes.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val path = Path()
        val rectf = RectF(paddingLeft.toFloat(), paddingTop.toFloat(),
            width.toFloat(), height.toFloat())
        if (arrowLocation == LOCATION_LEFT) {
            setLeftPath(rectf, path)
        } else {
            setRightPath(rectf, path)
        }
        canvas.drawPath(path, bitmapPaint)
    }

    private fun setRightPath(rectf: RectF, path: Path) {
        val left = rectf.left
        val top = rectf.top
        val right = rectf.right
        val bottom = rectf.bottom
        path.apply {
            moveTo(left+angle, top)
            lineTo(right-angle-arrowWidth, top)
            arcTo(RectF(right-angle*2-arrowWidth, top, right-arrowWidth, top+angle*2),
                270f, 90f)
            lineTo(right-arrowWidth, top+arrowTop)
            lineTo(right, top+arrowTop+arrowHeight/2)
            lineTo(right-arrowWidth, top+arrowTop+arrowHeight)
            lineTo(right-arrowWidth, bottom-angle)
            arcTo(RectF(right-angle*2-arrowWidth, bottom-angle*2, right-arrowWidth, bottom),
            0f, 90f)
            lineTo(left+angle, bottom)
            arcTo(RectF(left, bottom-angle*2, left+angle*2, bottom), 90f, 90f)
            lineTo(left, top+angle)
        }
    }

    private fun setLeftPath(rectf: RectF, path: Path) {
        path.apply {
            moveTo(angle+arrowWidth, rectf.top)
            lineTo(rectf.width(), rectf.top)
            arcTo(RectF(rectf.right-angle*2, rectf.top, rectf.right, rectf.top+angle*2),
                270f, 90f)
            lineTo(rectf.right, rectf.top)
            arcTo(RectF(rectf.right-angle*2, rectf.bottom-angle*2, rectf.right, rectf.bottom),
                0f, 90f)
            lineTo(rectf.left+arrowWidth, rectf.bottom)
            arcTo(RectF(rectf.left+arrowWidth, rectf.bottom-angle*2, arrowWidth+angle*2+rectf.left, rectf.bottom),
                90f, 90f)
            lineTo(rectf.left+arrowWidth, arrowTop+arrowHeight)
            lineTo(rectf.left, arrowTop+arrowHeight/2)
            lineTo(rectf.left+arrowWidth, arrowTop)
            lineTo(rectf.left+arrowWidth, rectf.top)
            arcTo(RectF(rectf.left+arrowWidth, rectf.top, rectf.left+arrowWidth+angle*2, rectf.top+angle*2),
                180f, 90f)
        }
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        bitmap = this@BubbleImageView.drawable.toBitmap()
        setup()
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        bitmap = bm
        setup()
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        bitmap = drawable.toBitmap()
        setup()
    }

    private fun setup() {
        if (bitmap == null) return

        bitmapShader = BitmapShader(bitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        bitmapPaint.shader = bitmapShader
        updateShaderMatrix()
        invalidate()
    }

    /**
     * 计算图片需要放大的比例
     */
    private fun updateShaderMatrix() {
        val bitmapWidth = bitmap!!.width
        val bitmapHeight = bitmap!!.height
        val scale: Float
        var dx = 0f
        var dy = 0f
        val shaderMatrix = Matrix()
        shaderMatrix.set(null)

        //计算bitmap的缩放比例，并且其x或者y轴的偏移
        if (bitmapWidth/width > bitmapHeight/height) {
            scale = (height / bitmapHeight).toFloat()
            dx = abs(bitmapWidth*scale - width) * 0.5f
        } else {
            scale = (width / bitmapWidth).toFloat()
            dy = abs(bitmapHeight*scale - height) * 0.5f
        }
        shaderMatrix.setScale(scale, scale)
        shaderMatrix.postTranslate(dx, dy)

        bitmapShader!!.setLocalMatrix(shaderMatrix)
    }
}