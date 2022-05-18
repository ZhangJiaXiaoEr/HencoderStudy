package cn.zectec.jetpackstudy.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.OnLifecycleEvent

class MyService : LifecycleService() {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun functhion1() {

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun functhion2() {

    }
}