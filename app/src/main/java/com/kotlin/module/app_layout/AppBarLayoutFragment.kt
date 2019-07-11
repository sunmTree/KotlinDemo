package com.kotlin.module.app_layout

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.demo.R
import kotlinx.android.synthetic.main.app_layout_fragment.*
import java.util.*

private const val TAG_LOG = "AppBarLayout"

class AppBarLayoutFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.app_layout_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        recycle.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        recycle.adapter = DemoAdapter(initData(), context)

        toolbar.title = "AppBarLayout-Demo"
        Log.d(TAG_LOG, "pro appBar length ${app_bar.totalScrollRange}")
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { p0, p1 ->
            Log.d(TAG_LOG, "listener appBar length ${p0.totalScrollRange} >> $p1")
            langAnim(Math.abs(p1), p0.totalScrollRange)
        })
    }

    // 下拉value变小，上滑变大
    private fun langAnim(value: Int, totalRange: Int) {
        var change = ((totalRange - value) * 1.00F / totalRange) * 0.5F
        var transY = ((totalRange - value) * 1.00F / totalRange)
        Log.d(TAG_LOG, "langAnim ${change + 1}")
        select_lang.scaleX = 1 + change
        select_lang.scaleY = 1+ change
        select_lang.translationY = select_lang.measuredHeight * transY
    }

    private fun initData() = Arrays.asList("Item1", "Item2", "Item3", "Item4", "Item5", "Item6", "Item7", "Item8", "Item9")


}