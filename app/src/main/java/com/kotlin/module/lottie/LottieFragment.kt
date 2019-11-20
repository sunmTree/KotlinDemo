package com.kotlin.module.lottie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kotlin.demo.R
import kotlinx.android.synthetic.main.lottie_fragment.*

class LottieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lottie_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog_button.setOnClickListener {
            ExitMatchConfirmDialog(context, View.OnClickListener {  }).show()
        }
    }
}