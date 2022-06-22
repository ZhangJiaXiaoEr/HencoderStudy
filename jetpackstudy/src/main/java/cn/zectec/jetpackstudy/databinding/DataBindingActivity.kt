package cn.zectec.jetpackstudy.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.zectec.jetpackstudy.R

class DataBindingActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDatabindingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.starInfo = StarInfo(R.mipmap.ic_launcher,
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_bt%2F0%2F13279445893%2F1000.jpg&refer=http%3A%2F%2Finews.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1656075533&t=ce582cd4f4d40dc1239f841ff2b2060d",
       "王心凌", 3 )
    }
}