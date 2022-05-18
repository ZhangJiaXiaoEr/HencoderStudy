package cn.zectec.jetpackstudy

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import cn.zectec.jetpackstudy.lifecycle.ApplicationObsever

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationObsever())
    }
}