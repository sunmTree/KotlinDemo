package com.kotlin.demo

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.module.SourceList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.module_item_layout.view.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycle.layoutManager = LinearLayoutManager(this)
        recycle.adapter = ModuleAdapter(this)
    }

}


class ModuleAdapter(context: Context): RecyclerView.Adapter<ModuleAdapter.ViewHolder>() {
    private val mContext: Context = context

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.module_item_layout, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return SourceList.getSourceList().size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val module = SourceList.getSourceList()[p1]
        p0.itemView.item_title.text = module.getModuleName()
        p0.itemView.setOnClickListener { module.onModuleClick(mContext) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
