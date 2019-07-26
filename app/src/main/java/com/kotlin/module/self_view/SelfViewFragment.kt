package com.kotlin.module.self_view

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import com.kotlin.demo.R
import kotlinx.android.synthetic.main.self_view_layout.*
import java.util.concurrent.TimeUnit

private const val APPEND_DOT_MSG = 0x012

class SelfViewFragment: Fragment(), Handler.Callback {
    private var mAppendCount: Int = 0
    private val mHandler: Handler = Handler(this@SelfViewFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.self_view_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        makeSureTextSize()
        appendText()
        startAnswerAnim()
        mHandler.sendEmptyMessageDelayed(APPEND_DOT_MSG, TimeUnit.SECONDS.toMillis(1))
    }

    private fun startAnswerAnim() {
        val anim = ValueAnimator.ofFloat(0F, 1F).setDuration(1000)
        anim?.apply { repeatCount = Animation.INFINITE
            repeatMode = ValueAnimator.RESTART
        }
        anim.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            ring_answer_anim.scaleX = (1 + (value * 0.5)).toFloat()
            ring_answer_anim.scaleY = (1 + (value * 0.5)).toFloat()
            ring_answer_anim.alpha = (0.6 * (1 - value)).toFloat()
        }
        anim.start()
    }

    private fun makeSureTextSize() {
        val append = "\t5231232 ${resources.getString(R.string.match_is_calling)} ..."
        val measureText = call_detail.paint.measureText(append)
        call_detail.text = append
        if (measureText > (resources.displayMetrics.widthPixels * 0.8)) {
            val preTextSize = call_detail.paint.textSize
            call_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
            Log.d("SelfView", "preTextSize $preTextSize spSize ${resources.getDimension(R.dimen.sp_16)} now size ${call_detail.paint.textSize}")
        }
    }

    private fun appendText() {
        val dot: String = when (mAppendCount % 4) {
            1 -> ".  "
            2 -> ".. "
            3 -> "..."
            else -> "   "
        }
        val append = "\t5231232 ${resources.getString(R.string.match_is_calling)} $dot"
        call_detail.text = append
        Log.d("SelfView", "append ${append.length} screenWidth ${resources.displayMetrics.widthPixels*0.8} measure width ${call_detail.paint.measureText(append)}")
        mAppendCount++
    }

    override fun handleMessage(msg: Message?): Boolean {
        appendText()
        mHandler.sendEmptyMessageDelayed(APPEND_DOT_MSG, TimeUnit.SECONDS.toMillis(1))
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }

}