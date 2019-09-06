package com.kotlin.module

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.demo.R
import com.kotlin.module.animation.TransitionFragment
import com.kotlin.module.app_layout.AppBarLayoutFragment
import com.kotlin.module.app_layout.NoAppBarLayoutFragment
import com.kotlin.module.camera.CameraFragment
import com.kotlin.module.exo.ExoPlayerFragment
import com.kotlin.module.image_decoder.ImageDecoderFragment
import com.kotlin.module.java.JavaSimpleFragment
import com.kotlin.module.self_view.SelfViewFragment
import com.kotlin.module.thread.ThreadFragment

const val MODULE_BEAN = "Module_Bean"

class BaseFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if(intent.getIntExtra(MODULE_BEAN, CAMERA_MODULE) == APP_BAR_MODULE) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_fragment)
        initFragment()
    }

    private fun initFragment() {
        val fragment = when (intent.getIntExtra(MODULE_BEAN, 0)) {
            CAMERA_MODULE -> CameraFragment()
            IMAGE_DECODER_MODULE -> ImageDecoderFragment()
            SELF_VIEW_MODULE -> SelfViewFragment()
            APP_BAR_MODULE -> AppBarLayoutFragment()
            NO_APP_BAR_MODULE -> NoAppBarLayoutFragment()
            TRANSITION_MODULE -> TransitionFragment()
            THREAD_MODULE -> ThreadFragment()
            EXO_MODULE -> ExoPlayerFragment()
            JAVA_MODULE -> JavaSimpleFragment()
            else -> DefaultFragment()
        }
        supportFragmentManager.beginTransaction().add(R.id.parent, fragment).commitAllowingStateLoss()
    }

    companion object {
        fun innerIntent(context: Context, module: Int) {
            BaseFragmentActivity().apply {
                val intent = Intent(context, this.javaClass)
                intent.putExtra(MODULE_BEAN, module)
                context.startActivity(intent)
            }
        }
    }
}
