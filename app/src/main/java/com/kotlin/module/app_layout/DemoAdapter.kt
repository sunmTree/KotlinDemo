package com.kotlin.module.app_layout

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.demo.R
import kotlinx.android.synthetic.main.demo_item_one.view.*

private const val FIRST_TYPE = 0x00
private const val SECOND_TYPE = 0X01

class DemoAdapter(itemList: List<String>, con: Context?): RecyclerView.Adapter<DemoAdapter.ViewHolder>() {
    private var dataList = itemList
    private var mContext = con

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return when(p1) {
            FIRST_TYPE -> ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.demo_item_one, p0, false))
            else -> ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.demo_item_two, p0, false))
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.itemView.content.text = dataList[p1]
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> FIRST_TYPE
            else -> SECOND_TYPE
        }
    }

    fun getData() = dataList

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)

}