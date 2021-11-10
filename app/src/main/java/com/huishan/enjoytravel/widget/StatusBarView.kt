package com.huishan.enjoytravel.widget

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable


class StatusBarView(context: Context, @Nullable attrs: AttributeSet?) :
    View(context, attrs) {
    constructor(context: Context) : this(context, null) {}

    protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            mStatusBarHeight
        )
    }

    companion object {
        private var mStatusBarHeight = 0

        //此处代码可以放到StatusBarUtils
        fun getStatusBarHeight(context: Context): Int {
            if (mStatusBarHeight == 0) {
                val res: Resources = context.getResources()
                val resourceId: Int = res.getIdentifier("status_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    mStatusBarHeight = res.getDimensionPixelSize(resourceId)
                }
            }
            return mStatusBarHeight
        }
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBarHeight =
                getStatusBarHeight(context)
        }
    }
}