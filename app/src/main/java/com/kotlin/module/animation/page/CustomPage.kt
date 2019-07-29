package com.kotlin.module.animation.page

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.Scene
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionValues
import com.kotlin.demo.R
import kotlinx.android.synthetic.main.custom_page.view.*

private const val CHANGE_SCENE_MSG = 0x0102
private const val MSG_DELAY_TIME = 1500L

class CustomPage(context: Context) : AbsPage(context) {
    private lateinit var mViewGroup: ViewGroup
    private var mCurrentScene = 0
    private val mTransition = CustomTransition()
    private lateinit var mSceneArray: Array<Scene>

    override fun createView(parent: ViewGroup): View? {
        return LayoutInflater.from(mContext).inflate(R.layout.custom_page, parent, true).apply {
            mViewGroup = this.custom_parent
        }
    }

    override fun init() {
        super.init()
        mSceneArray = arrayOf(
            Scene.getSceneForLayout(mViewGroup, R.layout.custom_one, mContext),
            Scene.getSceneForLayout(mViewGroup, R.layout.custom_two, mContext),
            Scene.getSceneForLayout(mViewGroup, R.layout.custom_thi, mContext)
        )

        TransitionManager.go(mSceneArray[mCurrentScene % mSceneArray.size], mTransition)
        mHandler.sendEmptyMessageDelayed(CHANGE_SCENE_MSG, MSG_DELAY_TIME)
    }

    override fun handleMessage(msg: Message?): Boolean {
        if (msg?.what == CHANGE_SCENE_MSG) {
            mCurrentScene++
            TransitionManager.go(mSceneArray[mCurrentScene % mSceneArray.size], mTransition)
            mHandler.sendEmptyMessageDelayed(CHANGE_SCENE_MSG, MSG_DELAY_TIME)
            return true
        }
        return super.handleMessage(msg)
    }
}


private const val PROPNAME_BACKGROUND = "com.kotlin.module:CustomTransition:Background"

class CustomTransition : Transition() {

    override fun captureEndValues(p0: TransitionValues) {
        captureValues(p0)
    }

    override fun captureStartValues(p0: TransitionValues) {
        captureValues(p0)
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        if (startValues == null || endValues == null) return null
        val view = endValues.view

        val startDrawable = startValues.values[PROPNAME_BACKGROUND] as Drawable
        val endDrawable = endValues.values[PROPNAME_BACKGROUND] as Drawable

        if (startDrawable is ColorDrawable && endDrawable is ColorDrawable) {
            if (startDrawable.color != endDrawable.color) {
                val valueAnimator = ValueAnimator.ofInt(startDrawable.color, endDrawable.color)
                valueAnimator.addUpdateListener { animation ->
                    val color = animation.animatedValue as Int
                    view.setBackgroundColor(color)
                }
                return valueAnimator
            }
        }

        return super.createAnimator(sceneRoot, startValues, endValues)
    }

    private fun captureValues(transitionValues: TransitionValues) {
        val view = transitionValues.view
        transitionValues.values[PROPNAME_BACKGROUND] = view.background
    }

}