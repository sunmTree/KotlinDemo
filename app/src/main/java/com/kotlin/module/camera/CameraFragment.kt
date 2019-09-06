package com.kotlin.module.camera

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.demo.R
import kotlinx.android.synthetic.main.camera_fragment.*


class CameraFragment: androidx.fragment.app.Fragment() {
    lateinit var pageOne: IntArray
    lateinit var pageTwo: IntArray
    lateinit var pageThird: IntArray
    lateinit var pageFour: IntArray
    lateinit var pageFive: IntArray

    private lateinit var animator: ValueAnimator


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.camera_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pageOne = intArrayOf(resources.getColor(R.color.page_1_top), resources.getColor(R.color.page_1_bo))
        pageTwo = intArrayOf(resources.getColor(R.color.page_2_top), resources.getColor(R.color.page_2_bo))
        pageThird = intArrayOf(resources.getColor(R.color.page_3_top), resources.getColor(R.color.page_3_bo))
        pageFour = intArrayOf(resources.getColor(R.color.page_4_top), resources.getColor(R.color.page_4_bo))
        pageFive = intArrayOf(resources.getColor(R.color.page_5_top), resources.getColor(R.color.page_5_bo))


        val colors = intArrayOf(pageOne[0], pageOne[1])

        val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)

        val argbEvaluator = ArgbEvaluator()

        animator = ValueAnimator.ofFloat(0F, 4F).setDuration(2000)
        animator.addUpdateListener { animation ->
            val animValue = animation.animatedValue as Float
            when {
                animValue > 3F -> {
                    colors[0] = argbEvaluator.evaluate(animValue - 3, pageFour[0], pageFive[0]) as Int
                    colors[1] = argbEvaluator.evaluate(animValue - 3, pageFour[1], pageFive[1]) as Int
                }
                animValue > 2F -> {
                    colors[0] = argbEvaluator.evaluate(animValue - 2, pageThird[0], pageFour[0]) as Int
                    colors[1] = argbEvaluator.evaluate(animValue - 2, pageThird[1], pageFour[1]) as Int
                }
                animValue > 1F -> {
                    colors[0] = argbEvaluator.evaluate(animValue - 1, pageTwo[0], pageThird[0]) as Int
                    colors[1] = argbEvaluator.evaluate(animValue - 1, pageTwo[1], pageThird[1]) as Int
                }
                else -> {
                    colors[0] = argbEvaluator.evaluate(animValue, pageOne[0], pageTwo[0]) as Int
                    colors[1] = argbEvaluator.evaluate(animValue, pageOne[1], pageTwo[1]) as Int
                }
            }
            gradientDrawable.colors = colors
        }
        animator.repeatCount = -1
        animator.start()


        image.setImageDrawable(gradientDrawable)

        do_change.setOnClickListener {
            val time = edit_time.text.toString()
            if (!TextUtils.isEmpty(time)) {
                animator.cancel()
                animator.duration = time.toInt().toLong()
                animator.start()
            }
        }
    }

}