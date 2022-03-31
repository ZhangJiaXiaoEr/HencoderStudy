package com.zjx.customview.L17

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class MaterialEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val FLOATINGLABEL_TEXTSIZE = 40f
    private val FLOATINGLABEL_INTERVAL = 20f
    private var floatingLabelFraction = 0f
        get
        set(value) {
            field = value
            invalidate()
        }
    private lateinit var animator: ObjectAnimator
    private fun animatorIsInitialized() =:: animator.isInitialized
    private var floatingLabelShowed = false
    var useFloatingLabel = true
        set(value) {
            field = value
            useFloatingLabel()
        }

    init {

        useFloatingLabel()
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (useFloatingLabel) {
                    if (floatingLabelShowed && TextUtils.isEmpty(s.toString())) {
                        floatingLabelShowed = !floatingLabelShowed
                        getAnimator().reverse()
                    } else if (!floatingLabelShowed && !TextUtils.isEmpty(s.toString())) {
                        floatingLabelShowed = !floatingLabelShowed
                        getAnimator().start()
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun useFloatingLabel () {
        val padding = Rect()
        background.getPadding(padding)
        if (useFloatingLabel) {
            setPadding(leftPaddingOffset,
                (padding.top+FLOATINGLABEL_TEXTSIZE+FLOATINGLABEL_INTERVAL).toInt()
                , rightPaddingOffset, bottomPaddingOffset)
        } else {
            setPadding(leftPaddingOffset, padding.top, rightPaddingOffset, bottomPaddingOffset)
        }
    }

    private fun getAnimator (): ObjectAnimator{
        if (!animatorIsInitialized()) {
            animator = ObjectAnimator.ofFloat(this, "floatingLabelFraction", 1f)
        }
        return animator
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (useFloatingLabel && !TextUtils.isEmpty(hint.toString())) {
            paint.alpha = (0xff * floatingLabelFraction).toInt()
            paint.textSize = FLOATINGLABEL_TEXTSIZE + Math.abs(textSize-FLOATINGLABEL_TEXTSIZE)*(1-floatingLabelFraction)
            val y = FLOATINGLABEL_TEXTSIZE + (1-floatingLabelFraction)*(textSize+FLOATINGLABEL_INTERVAL)
            canvas.drawText(hint.toString(), leftPaddingOffset.toFloat(), y, paint)
        }
    }
}