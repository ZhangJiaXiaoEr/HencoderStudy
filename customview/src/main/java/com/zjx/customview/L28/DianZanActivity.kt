package com.zjx.customview.L28

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.zjx.customview.R
import com.zjx.customview.databinding.ActivityDianzanBinding
import com.zjx.customview.inflate

class DianZanActivity: AppCompatActivity(R.layout.activity_dianzan) {
    val binding: ActivityDianzanBinding by inflate()
    private var forward: Boolean = true
    private var animating = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.imageview.setOnClickListener {
            if (animating) return@setOnClickListener
            animating = true
            if (forward) {
                binding.motionLayout.setTransition(R.id.forward)
            } else {
                binding.motionLayout.setTransition(R.id.revert)
            }
            binding.motionLayout.transitionToEnd()
        }
        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                animating = false
                forward = !forward
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

        })
    }
}