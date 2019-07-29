package com.kotlin.module.animation.page

import android.content.Context
import androidx.transition.Scene
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.demo.R

class FirstPage(context: Context) : AbsPage(context) {
    private lateinit var mSceneView: ViewGroup

    override fun createView(parent: ViewGroup): View {
        val parent = LayoutInflater.from(mContext).inflate(R.layout.first_page, parent, true)
        mSceneView = parent.findViewById(R.id.scene_root)
        return parent
    }

    override fun init() {
        super.init()
        val aScene = Scene.getSceneForLayout(mSceneView, R.layout.a_scene, mContext)
        val anotherScene = Scene.getSceneForLayout(mSceneView, R.layout.another_scene, mContext)
        val fadeTransition = TransitionInflater.from(mContext).inflateTransition(R.transition.fade_transition)

        mHandler.postDelayed({ TransitionManager.go(anotherScene, fadeTransition) }, 1000)
    }

}