package com.kotlin.module.animation.page

import android.content.Context
import android.transition.Fade
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.demo.R

class DelayPage(context: Context) : AbsPage(context) {
    private lateinit var mViewParent: ViewGroup

    override fun init() {
        super.init()
        val labelText = TextView(mContext).apply {
            text = "Label"
            id = R.id.text
        }
        val fade = Fade(Fade.IN)
        TransitionManager.beginDelayedTransition(mViewParent, fade)
        mHandler.postDelayed({ mViewParent.addView(labelText) }, 2000)

    }

    override fun createView(parent: ViewGroup): View {
        val viewParent = LayoutInflater.from(mContext).inflate(R.layout.delay_page, parent, true)
        mViewParent = viewParent.findViewById(R.id.delay_parent)
        return viewParent
    }

}