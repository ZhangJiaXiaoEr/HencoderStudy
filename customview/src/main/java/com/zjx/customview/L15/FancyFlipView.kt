package com.zjx.customview.L15

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.zjx.customview.R
import com.zjx.customview.getBitmap

class FancyFlipView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val picWidth = 600
    private val padding = 100f
    private var bitmapX:Float = 0f
    private var bitmapY:Float = 0f
    private var bitmap: Bitmap = getBitmap(resources, R.mipmap.xiaoxin, picWidth)
    private val camera = Camera()
    private var lastCameraRotateX = 0f
    private var cameraRotateX = 0f
        get() = field
        set(value) {
            field = value
            camera.rotateX(value-lastCameraRotateX)
            lastCameraRotateX = value
            invalidate()
        }
    private var canvasRotateAngle = 0f
        get() = field
        set(value) {
            field = value
        }


    init {
        camera.setLocation(0f, 0f,  -5*resources.displayMetrics.density)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmapX = (width/2 - picWidth/2).toFloat()
        bitmapY = (height/2 - picWidth/2).toFloat()
    }

    @SuppressLint("DrawAllocation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas) {
        /*********************    实现翻页的效果   ****************************/
        //实现上半部分的裁剪
        canvas.save()
        canvas.translate(padding+picWidth/2, padding+picWidth/2)
        canvas.rotate(-canvasRotateAngle)
        canvas.clipRect(-(picWidth/2+150).toFloat(),  -(picWidth/2+150).toFloat(), (picWidth/2+150).toFloat(), 0f)
        canvas.rotate(canvasRotateAngle)
        canvas.translate(-(padding+picWidth/2), -(padding+picWidth/2))
        canvas.drawBitmap(bitmap, padding, padding, paint)
        canvas.restore()

        //实现下半部分的斜着的效果
        canvas.save()
        canvas.translate(padding+picWidth/2, padding+picWidth/2)
        canvas.rotate(-canvasRotateAngle)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-(picWidth/2+150).toFloat(), 0f, (picWidth/2+150).toFloat(), (picWidth/2+150).toFloat())
        canvas.rotate(canvasRotateAngle)
        canvas.translate(-(padding+picWidth/2), -(padding+picWidth/2))
        canvas.drawBitmap(bitmap, padding, padding, paint)
        canvas.restore()
    }
}