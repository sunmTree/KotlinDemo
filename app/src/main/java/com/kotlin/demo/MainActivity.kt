package com.kotlin.demo

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.module.SourceList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.module_item_layout.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycle.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recycle.adapter = ModuleAdapter(this)
    }

}


class ModuleAdapter(context: Context): androidx.recyclerview.widget.RecyclerView.Adapter<ModuleAdapter.ViewHolder>() {
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
        p0.itemView.setBackgroundColor(randomColor())
    }

    inner class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

    private fun randomColor() : Int {
        val r = (Math.random() * 255).toInt()
        val g = (Math.random() * 255).toInt()
        val b = (Math.random() * 255).toInt()
        return Color.argb(0x44, r, g, b)
    }

}
