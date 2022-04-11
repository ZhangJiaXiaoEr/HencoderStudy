package com.zjx.customview.L28

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.zjx.customview.R

class MyImage(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {
    fun changeImage() {
        this.setImageResource(R.mipmap.ic_launcher_round)
    }

    fun reverImage() {
        this.setImageResource(R.mipmap.xiaoxin)
    }
}