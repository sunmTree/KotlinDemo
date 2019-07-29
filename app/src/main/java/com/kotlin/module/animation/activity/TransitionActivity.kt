package com.kotlin.module.animation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.demo.R
import com.kotlin.module.animation.page.*
import kotlinx.android.synthetic.main.activity_transition.*

const val PAGE_ARG = "page"
const val FIRST_PAGE = 0X001
const val DELAY_PAGE = 0x002
const val CUSTOM_PAGE = 0x003
const val TRANSITION_PAGE = 0x004

class TransitionActivity : AppCompatActivity() {
    private lateinit var mPage: AbsPage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition)

        mPage = when(intent.getIntExtra(PAGE_ARG, FIRST_PAGE)) {
            DELAY_PAGE -> DelayPage(TransitionActivity@this)
            CUSTOM_PAGE -> CustomPage(TransitionActivity@this)
            TRANSITION_PAGE -> TransitionPage(TransitionActivity@this)
            else -> FirstPage(TransitionActivity@this)
        }
        mPage.createView(parent = container)
        mPage.init()
    }

    companion object {
        fun launch(context: Context, page: Int) {
            TransitionActivity().apply {
                val intent = Intent(context, this.javaClass)
                intent.putExtra(PAGE_ARG, page)
                context.startActivity(intent)
            }
        }
    }
}
