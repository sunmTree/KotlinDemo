package com.kotlin.module

import android.content.Context

interface IModuleBean {
    fun getModuleName(): String
    fun onModuleClick(context: Context)
}

const val CAMERA_MODULE = 1
const val IMAGE_DECODER_MODULE = 2
const val SELF_VIEW_MODULE = 3
const val APP_BAR_MODULE = 4
const val NO_APP_BAR_MODULE = 5
const val TRANSITION_MODULE = 6

class CameraModuleBean : IModuleBean {
    override fun getModuleName(): String = "Android Camera Demo"

    override fun onModuleClick(context: Context) {
        BaseFragmentActivity.innerIntent(context, CAMERA_MODULE)
    }
}

class ImageDecoderBean: IModuleBean {
    override fun getModuleName(): String = "Image Decoder"

    override fun onModuleClick(context: Context) {
        BaseFragmentActivity.innerIntent(context, IMAGE_DECODER_MODULE)
    }
}

class SelfViewBean: IModuleBean {
    override fun getModuleName(): String = "Self View"

    override fun onModuleClick(context: Context) {
        BaseFragmentActivity.innerIntent(context, SELF_VIEW_MODULE)
    }
}

class AppBarLayoutBean: IModuleBean {
    override fun getModuleName(): String = "App Bar Layout"

    override fun onModuleClick(context: Context) {
        BaseFragmentActivity.innerIntent(context, APP_BAR_MODULE)
    }
}

class NoAppBarLayoutBean: IModuleBean {
    override fun getModuleName(): String = "No App Bar Layout"

    override fun onModuleClick(context: Context) {
        BaseFragmentActivity.innerIntent(context, NO_APP_BAR_MODULE)
    }
}

class TransitionBean : IModuleBean {
    override fun getModuleName(): String = "Transition"

    override fun onModuleClick(context: Context) {
        BaseFragmentActivity.innerIntent(context, TRANSITION_MODULE)
    }
}

object SourceList {
    fun getSourceList() = arrayOf(CameraModuleBean(), ImageDecoderBean()
        , SelfViewBean(), AppBarLayoutBean(), NoAppBarLayoutBean(),
        TransitionBean())
}