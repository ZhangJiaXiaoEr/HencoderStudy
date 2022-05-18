package cn.zectec.jetpackstudy.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import cn.zectec.jetpackstudy.databinding.ActivityViewmodelBinding
import java.util.*

class VieModelActivity: AppCompatActivity() {
    lateinit var binding: ActivityViewmodelBinding
    lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewmodelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, AndroidViewModelFactory(application)).get(MyViewModel::class.java)
        viewModel.currentSecond.observe(this) {
            binding.textView.text = it.toString()
        }
        start()
    }

    fun start() {
        Timer().schedule(object : TimerTask(){
            override fun run() {
                viewModel.currentSecond.postValue(binding.textView.text.toString().toInt() + 1)
            }

        }, 1000L, 1000L)
    }


}