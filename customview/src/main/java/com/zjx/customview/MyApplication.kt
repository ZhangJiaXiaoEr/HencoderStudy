package com.zjx.customview

import android.app.Application

class MyApplication: Application() {

    companion object {
        @JvmStatic
        val instance = this
    }
}