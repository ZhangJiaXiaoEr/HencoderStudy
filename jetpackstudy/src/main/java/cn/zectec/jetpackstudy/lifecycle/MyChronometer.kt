package cn.zectec.jetpackstudy.lifecycle

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.widget.Chronometer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MyChronometer(context: Context, attrs: AttributeSet?) : Chronometer(context, attrs), LifecycleObserver {
    private var passTime: Long = 0L
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startMeter() {
        base = SystemClock.elapsedRealtime() - passTime
        start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopMeter() {
        passTime = SystemClock.elapsedRealtime() - base
        stop()
    }
}