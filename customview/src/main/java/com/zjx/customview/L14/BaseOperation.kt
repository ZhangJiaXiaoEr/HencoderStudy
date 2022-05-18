package com.zjx.customview.L14

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.zjx.customview.R
import com.zjx.customview.getBitmap

class BaseOperation(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val picWidth = 600
    private val padding = 100f
    private var bitmapX:Float = 0f
    private var bitmapY:Float = 0f
    private var bitmap: Bitmap = getBitmap(resources, R.mipmap.xiaoxin, picWidth)
    private val camera = Camera()

    init {
        camera.rotateX(40f)
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
        /*********************    裁剪    ****************************/
        //裁剪完之后剩下RectF中间的部分
//        canvas.clipRect(RectF(padding+100, padding+100, padding+picWidth-100, padding+picWidth-100))
        //裁剪完之后去掉RectF中间的部分
//        canvas.clipOutRect(RectF(padding+100, padding+100, padding+picWidth-100, padding+picWidth-100))
        //裁剪之后变成圆形
//        val clipPath = Path()
//        clipPath.addArc(saveRectF, 0f, 360f)
//        canvas.clipPath(clipPath)

        /*********************    几何变换    ****************************/
        //移动
//        canvas.translate(200f, 200f)
        //旋转(以0,0进行旋转)
//        canvas.rotate(30f)
        //旋转（以px，和py为中心进行旋转）
//        canvas.rotate(90f, padding+picWidth/2, padding+picWidth/2)
        //放缩（以0,0为起点为中心放缩）
//        canvas.scale(2f, 2f)
        //放缩（以px，和py为中心放缩）
//        canvas.scale(1.5f, 1.5f, padding+picWidth/2, padding+picWidth/2)

        /*********************    多重变换    ****************************/
//        canvas.translate(200f, 200f)
//        canvas.scale(1.5f, 1.5f, padding+picWidth/2, padding+picWidth/2)
//        canvas.rotate(90f, padding+picWidth/2, padding+picWidth/2)

        /*********************    Matrix的几何变换    ****************************/
        val matrix = Matrix()
        matrix.preRotate(90f, padding+picWidth/2, padding+picWidth/2)
        matrix.postRotate(90f, padding+picWidth/2, padding+picWidth/2)
        matrix.postTranslate(100f, 100f)
        matrix.postSkew(0.5f, 0.5f, padding+picWidth/2, padding+picWidth/2)//倾斜
        canvas.setMatrix(matrix)
        canvas.drawBitmap(bitmap, padding, padding, paint)

        /*********************    Camera的使用   ****************************/
        //简单的使用，沿着X轴旋转30°， 代码倒着写
//        canvas.translate(padding+picWidth/2, padding+picWidth/2)
//        camera.applyToCanvas(canvas)
//        canvas.translate(-(padding+picWidth/2), -(padding+picWidth/2))
//        canvas.drawBitmap(bitmap, padding, padding, paint)
    }
}