package com.huishan.enjoytravel.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(var leftSpace: Int, var topSpace: Int, var rightSpace: Int, var bottomSpace: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = leftSpace
        outRect.right = rightSpace
        outRect.bottom = bottomSpace

        //第一个Item不设置上边距
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = topSpace
        }
    }
}