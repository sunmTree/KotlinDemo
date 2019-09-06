package com.kotlin.module.image_decoder

import android.animation.*
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.LinearGradient
import android.graphics.Rect
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.demo.R
import kotlinx.android.synthetic.main.image_decoder_layout.*


class ImageDecoderFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.image_decoder_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val createSource = ImageDecoder.createSource(resources, R.drawable.crazy_car)
            image.setImageBitmap(ImageDecoder.decodeBitmap(createSource
            ) { decoder, info, source ->
                decoder.setTargetSampleSize(2)
                val cropRect = Rect(0, 0, info.size.width / 2, info.size.height / 2)
                decoder.crop = cropRect
            })
        } else {
            image.setImageResource(R.drawable.crazy_car)
        }

        match_bg.setBackgroundResource(R.drawable.match_anim)
        val matchAnim = match_bg.background
        if (matchAnim is Animatable)
            matchAnim.start()

        var firstColor = Color.rgb(0xF6, 0x53, 0xFF)
        var secondColor = Color.rgb(0x75, 0x19, 0xFF)
        var colors = intArrayOf(firstColor, secondColor)
        var gradientDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)


        var valueAnim:ValueAnimator = ValueAnimator.ofFloat(0F, 1f)
            .apply {
                duration = 1000
                repeatCount = -1
            }

        valueAnim.addUpdateListener {
            val i = it.animatedValue as Float
            if (i < 4F) {
                gradientDrawable.colors = update(i)
            }
        }
        valueAnim.start()
        match_bg2.setImageDrawable(gradientDrawable)
    }

    private fun update(anim: Float): IntArray {
        var argbEvaluator = ArgbEvaluator()
        when {
            anim > 3F -> {
                // #F33286 #7519FF F653FF #7519FF
                var top1 = Color.rgb(0xf3, 0x32, 0x86)
                var bottom1 = Color.rgb(0x75, 0x19, 0xff)
                var top2 = Color.rgb(0xf6, 0x53, 0xff)
                var bottom2 = Color.rgb(0x75, 0x19, 0xff)

                val evaluate = argbEvaluator.evaluate(anim - 1, top1, top2)
                val evaluate1 = argbEvaluator.evaluate(anim - 1, bottom1, bottom2)
                return intArrayOf(evaluate as Int, evaluate1 as Int)
            }
            anim > 2F -> {
                // #F33286 #62AEFF  #F33286 #7519FF
                var top1 = Color.rgb(0xf3, 0x32, 0x86)
                var bottom1 = Color.rgb(0x62, 0xAE, 0xff)
                var top2 = Color.rgb(0xf3, 0x32, 0x86)
                var bottom2 = Color.rgb(0x75, 0x19, 0xff)

                val evaluate = argbEvaluator.evaluate(anim - 1, top1, top2)
                val evaluate1 = argbEvaluator.evaluate(anim - 1, bottom1, bottom2)
                return intArrayOf(evaluate as Int, evaluate1 as Int)
            }
            anim > 1F -> {
                //  #F653FF  #62AEFF #F33286 #62AEFF
                var top1 = Color.rgb(0xf6, 0x53, 0xff)
                var bottom1 = Color.rgb(0x62, 0xAE, 0xff)
                var top2 = Color.rgb(0xf3, 0x32, 0x86)
                var bottom2 = Color.rgb(0x62, 0xAE, 0xff)

                val evaluate = argbEvaluator.evaluate(anim - 1, top1, top2)
                val evaluate1 = argbEvaluator.evaluate(anim - 1, bottom1, bottom2)
                return intArrayOf(evaluate as Int, evaluate1 as Int)
            }
            else -> {
                //  #F653FF  #7519FF #F653FF #62AEFF
                var top1 = Color.rgb(0xf6, 0x53, 0xff)
                var bottom1 = Color.rgb(0x75, 0x19, 0xff)
                var top2 = Color.rgb(0xf6, 0x53, 0xff)
                var bottom2 = Color.rgb(0x62, 0xAE, 0xff)

                val evaluate = argbEvaluator.evaluate(anim, top1, top2)
                val evaluate1 = argbEvaluator.evaluate(anim, bottom1, bottom2)
                return intArrayOf(evaluate as Int, evaluate1 as Int)
            }
        }
    }

}