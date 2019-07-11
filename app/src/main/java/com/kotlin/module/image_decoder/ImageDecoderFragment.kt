package com.kotlin.module.image_decoder

import android.graphics.ImageDecoder
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.demo.R
import kotlinx.android.synthetic.main.image_decoder_layout.*

class ImageDecoderFragment : Fragment() {

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
    }

}