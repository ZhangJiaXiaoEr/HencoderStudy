package cn.zectec.jetpackstudy.databinding.shuangxiangbind

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import cn.zectec.jetpackstudy.BR

class ViewModel: BaseObservable() {
    val user: UserInfo
    init {
        user = UserInfo("Julia")
    }

    @Bindable
    fun getUserName(): String {
        return user.name
    }

    fun setUserName(name: String) {
        user.name = name
        notifyPropertyChanged(BR.userName)
    }
}