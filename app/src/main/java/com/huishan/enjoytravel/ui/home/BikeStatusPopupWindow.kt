package com.huishan.enjoytravel.ui.home

import android.content.Context
import android.graphics.drawable.PaintDrawable
import android.view.*
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.entity.ItemPopBikeStatusEntity
import com.huishan.enjoytravel.databinding.PopwindowBikeStatusBinding
import com.huishan.enjoytravel.widget.GridSpacingItemDecoration
import com.huishan.enjoytravel.ui.MyBindingAdapter

class BikeStatusPopupWindow(
    context: Context,
    val parent: View,
    val viewModel: HomeViewModel
) {
    companion object {
        const val TAG = "BikeStatusPopupWindow"
    }

    private var viewDataBinding: PopwindowBikeStatusBinding
    var popWindow: PopupWindow

    init {
        val inflater = LayoutInflater.from(parent.context)
        viewDataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.popwindow_bike_status,
            parent as ViewGroup,
            false
        )
        viewDataBinding.viewmodel = viewModel
        val width = parent.width * 9 / 10
        val height = parent.height
        popWindow = PopupWindow(viewDataBinding.root, width, height)
        popWindow.isFocusable = true
        popWindow.isOutsideTouchable = true
        popWindow.isTouchable = true
        popWindow.setBackgroundDrawable(PaintDrawable(0x000000))
        popWindow.animationStyle = R.style.pop_anim_style

        viewDataBinding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        viewDataBinding.recyclerView.addItemDecoration(GridSpacingItemDecoration(3, 50, false))
        val adapter = MyBindingAdapter<ItemPopBikeStatusEntity>(context, R.layout.item_pop_bike_status, viewModel, viewModel.entityList)
        viewDataBinding.recyclerView.adapter = adapter
    }


    fun show() {
        if (!popWindow.isShowing) {
            popWindow.showAtLocation(parent, Gravity.RIGHT or Gravity.BOTTOM, 0, 0)
        }
    }

    fun dismiss() {
        if (popWindow.isShowing) {
            popWindow.dismiss()
        }
    }
}