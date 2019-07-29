package com.kotlin.module.animation.page

import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.kotlin.demo.R
import com.kotlin.module.animation.activity.NextTransActivity

class TransitionPage(context: Context) : AbsPage(context) {
    private lateinit var mIntentBtn: Button
    private lateinit var mTextView: TextView

    override fun createView(parent: ViewGroup): View? {
        return LayoutInflater.from(mContext).inflate(R.layout.transition_page_layout, parent, true).apply {
            mIntentBtn = this.findViewById(R.id.intent)
            mTextView = this.findViewById(R.id.next_title)
        }
    }

    override fun init() {
        super.init()

        mIntentBtn.setOnClickListener {
            val intent = Intent(mContext, NextTransActivity::class.java)
            val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(mContext as AppCompatActivity, mTextView, "hello_world")
            mContext.startActivity(intent, compat.toBundle())
        }
    }

}