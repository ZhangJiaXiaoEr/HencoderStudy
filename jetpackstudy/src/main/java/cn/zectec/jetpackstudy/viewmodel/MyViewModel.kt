package cn.zectec.jetpackstudy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    val currentSecond: MutableLiveData<Int> = MutableLiveData()

    fun getData():MutableLiveData<Int>  {
        return currentSecond
    }
}