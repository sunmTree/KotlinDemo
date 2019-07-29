package com.kotlin.module.animation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.demo.R
import com.kotlin.module.animation.activity.*
import kotlinx.android.synthetic.main.transition_fragment_layout.*

class TransitionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.transition_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transition.setOnClickListener { context?.apply { TransitionActivity.launch(this, FIRST_PAGE) } }
        delay.setOnClickListener { context?.apply { TransitionActivity.launch(this, DELAY_PAGE) } }
        custom.setOnClickListener { context?.apply { TransitionActivity.launch(this, CUSTOM_PAGE) } }
        transition_activity.setOnClickListener { context?.apply { TransitionActivity.launch(this, TRANSITION_PAGE) } }
    }

}