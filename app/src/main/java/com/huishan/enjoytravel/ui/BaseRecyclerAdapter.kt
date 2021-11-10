package com.huishan.enjoytravel.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T, B : ViewDataBinding>(val mContext: Context, @LayoutRes val layoutId: Int, var list: List<T>? = null) :
    RecyclerView.Adapter<RecyclerHolder<B>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder<B> {
        val binding: B = DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutId, parent, false)
        return RecyclerHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerHolder<B>, position: Int) {
        val t: T = list!![position]
        onBind(holder, t, position)
    }

    override fun getItemCount(): Int {
        return if (list == null) 0 else list!!.size
    }

    protected abstract fun onBind(holder: RecyclerHolder<B>, t: T, position: Int)

    @SuppressLint("NotifyDataSetChanged")
    open fun resetList(newList: List<T>) {
        list = newList
        notifyDataSetChanged()
    }
}