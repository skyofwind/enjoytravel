package com.huishan.enjoytravel.ui

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.huishan.enjoytravel.BR

class MyBindingAdapter<T>(mContext: Context, @LayoutRes layoutId: Int, val viewModel: ViewModel, list: List<T>) : BaseRecyclerAdapter<T, ViewDataBinding>(mContext, layoutId, list) {
    override fun onBind(holder: RecyclerHolder<ViewDataBinding>, t: T, position: Int) {
        holder.binding.setVariable(BR.item, t)
        holder.binding.setVariable(BR.viewmodel, viewModel)
        holder.binding.executePendingBindings()
    }
}