package com.kotlin.module.app_layout

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.demo.R
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import kotlinx.android.synthetic.main.app_layout_fragment.*
import java.util.*

private const val TAG_LOG = "AppBarLayout"

class AppBarLayoutFragment : Fragment() {
    private val layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
    private lateinit var mAdapter: DemoAdapter
    private val mHandler = Handler()

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
        recycle.layoutManager = layoutManager
        mAdapter = DemoAdapter(initData(), context)
        recycle.adapter = mAdapter

        toolbar.title = "AppBarLayout-Demo"
        Log.d(TAG_LOG, "pro appBar length ${app_bar.totalScrollRange}")
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { p0, p1 ->
            Log.d(TAG_LOG, "listener appBar length ${p0.totalScrollRange} >> $p1")
            langAnim(Math.abs(p1), p0.totalScrollRange)
        })
        srl_wrapper.setOnRefreshListener {
            Log.d(TAG_LOG, "show refresh finish")
            mHandler.postDelayed({ srl_wrapper.finishRefresh() }, 1000L)
        }
        srl_wrapper.setOnLoadmoreListener {
            Log.d(TAG_LOG, "show load more finish")
            mHandler.postDelayed({ srl_wrapper.finishLoadmore() }, 1000L)
        }
        srl_wrapper.isEnableLoadmore = true
        recycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var positions = IntArray(2)
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d(
                    TAG_LOG,
                    "onScrolled $dx > $dy currentPosition ${layoutManager.findLastVisibleItemPositions(
                        positions
                    )} ${positions[0]} + ${positions[1]}"
                )
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.d(TAG_LOG, "onScrollStateChange newState $newState")
                val lastIndex = mAdapter.getData().size - 1
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && (positions[0] == lastIndex || positions[1] == lastIndex)
                ) {
                    var lastView = recyclerView.getChildAt(recyclerView.childCount - 1)
                    if ((lastView.top - recyclerView.bottom >= lastView.measuredHeight).apply {
                            Log.d(
                                TAG_LOG, "view show height ${lastView.top - recyclerView.bottom}"
                            )
                        })
                        srl_wrapper.autoLoadmore()
                }
            }

        })
    }

    // 下拉value变小，上滑变大
    private fun langAnim(value: Int, totalRange: Int) {
        var change = ((totalRange - value) * 1.00F / totalRange) * 0.5F
        var transY = ((totalRange - value) * 1.00F / totalRange)
        select_lang.scaleX = 1 + change
        select_lang.scaleY = 1 + change
        select_lang.translationY = select_lang.measuredHeight * transY
    }

    private fun initData() = Arrays.asList(
        "Item1",
        "Item2",
        "Item3",
        "Item4",
        "Item5",
        "Item6",
        "Item7",
        "Item8",
        "Item9"
    )


}