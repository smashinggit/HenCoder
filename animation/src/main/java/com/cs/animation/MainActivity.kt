package com.cs.animation

import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        ivAvatar.animate()
//            .setDuration(5000)
//            .translationX(500f)
//            .setInterpolator(AccelerateInterpolator())
//            .start()


        val animator = ObjectAnimator.ofObject(provinceView, "province", ProvinceEvaluator(), "宁夏")
        animator.duration = 5000
//        animator.startDelay = 1000
        animator.start()
    }

    class ProvinceEvaluator : TypeEvaluator<String> {

        override fun evaluate(fraction: Float, startValue: String, endValue: String): String {

            Log.e("tag", "startValue: $startValue   endValue:$endValue")
            val startIndex = ProvinceView.PROVINCES.indexOf(startValue)
            val endIndex = ProvinceView.PROVINCES.indexOf(endValue)
            val index = (startIndex + (endIndex - startIndex) * fraction).toInt()
            return ProvinceView.PROVINCES[index]
        }
    }
}
