package com.zjx.customview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.zjx.customview.L28.DianZanActivity
import com.zjx.customview.L28.KeyFrameActivity
import com.zjx.customview.L28.MotionLayoutActivity1

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    val binding: com.zjx.customview.databinding.ActivityMainBinding by inflate()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        objectAnimatorText()
        click()
    }

    private fun click() {
//        binding.simpleMotion.setOnClickListener {
//            startActivity(Intent(this@MainActivity, MotionLayoutActivity1::class.java))
//        }
//        binding.keyframeActivity.setOnClickListener {
//            startActivity(Intent(this@MainActivity, KeyFrameActivity::class.java))
//        }
//        binding.dianzanActivity.setOnClickListener {
//            startActivity(Intent(this@MainActivity, DianZanActivity::class.java))
//        }
    }

    private fun viewPropertyAnimator() {
//        val animator = binding.image.animate()
//        animator.translationX(500f)
//        animator.translationY(500f)
//        animator.startDelay = 1000L
//        animator.duration = 2000L
//        //先慢后快的查值器
//        animator.interpolator = AccelerateInterpolator()
//        //先快后慢的插值器
//        animator.interpolator = DecelerateInterpolator()
//        animator.interpolator = AccelerateDecelerateInterpolator()
//        animator.start()
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun objectAnimatorText () {
//        val animator1 = ObjectAnimator.ofFloat(binding.fancyFlipView, "cameraRotateX", 40f)
//        animator1.duration = 1500L
//        val animator2 = ObjectAnimator.ofFloat(binding.fancyFlipView, "canvasRotateAngle", 360f)
//        animator2.duration = 2000L
//        val animatorSet = AnimatorSet()
//        animatorSet.playSequentially(animator1, animator2)
//        animatorSet.startDelay = 1000L
//        animatorSet.start()
    }

}