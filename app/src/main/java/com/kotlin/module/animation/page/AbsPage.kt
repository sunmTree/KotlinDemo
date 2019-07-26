package com.kotlin.module.animation.page

import android.content.Context
import android.os.Handler
import android.os.Message
import android.support.annotation.CallSuper
import android.view.View
import android.view.ViewGroup

open class AbsPage(context: Context): Handler.Callback {
    lateinit var mHandler: Handler
    val mContext = context

    @CallSuper
    open fun init() {
        mHandler = Handler(this)
    }
    open fun createView(parent: ViewGroup): View? = null

    override fun handleMessage(msg: Message?): Boolean {
        return false
    }
}