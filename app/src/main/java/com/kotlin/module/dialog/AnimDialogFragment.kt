package com.kotlin.module.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import com.kotlin.demo.R

class AnimDialogFragment : DialogFragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setStyle(STYLE_NORMAL, R.style.custom_dialog_style)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.anim_dialog_fragment, container, false)
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
//            setBackgroundDrawable(ColorDrawable(Color.argb(0x99, 0x00, 0x00, 0x00)))
            setLayout(resources.displayMetrics.widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT)
//            addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//            attributes.dimAmount = 0.6f
            Log.d("AnimDialog", "alpha ${attributes.alpha} dimAmount ${attributes.dimAmount}")
        }
    }
}