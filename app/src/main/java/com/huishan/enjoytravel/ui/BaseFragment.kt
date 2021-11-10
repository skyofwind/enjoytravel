package com.huishan.enjoytravel.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.huishan.enjoytravel.BikeApplication
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.util.ScreenUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    companion object {
        const val DEFAULT_WIDTH_NORMAL = 24f
        const val DEFAULT_HEIGHT_NORMAL = 24f
        const val DEFAULT_WIDTH_SMALL = 12f
        const val DEFAULT_HEIGHT_SMALL = 12f
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        setCurrentFragmentBackStatus()
    }

    abstract fun getLayoutResId(): Int

    abstract fun initData(view: View, savedInstanceState: Bundle?)

    fun setTitleText(view: View, title: String?) {
        val titleTextView = view.findViewById<TextView>(R.id.tv_title)
        titleTextView.text = title
    }

    fun setDefaultTitle(
        view: View,
        titleStr: String,
        @DrawableRes titleFollowIcon: Int?,
        @DrawableRes leftIcon: Int?,
        @DrawableRes rightIcon: Int?,
        clickListener: View.OnClickListener?,
        @ColorRes background: Int = R.color.white
    ) {
        val titleTextView = view.findViewById<TextView>(R.id.tv_title)
        val titleFollowImageView = view.findViewById<ImageView>(R.id.iv_title_icon)
        val leftImageView = view.findViewById<ImageView>(R.id.iv_left)
        val rightImageView = view.findViewById<ImageView>(R.id.iv_right)
        view.findViewById<ConstraintLayout>(R.id.cl_title).setBackgroundColor(getColor(background))

        setTitle(
            titleTextView,
            titleStr,
            titleFollowImageView,
            titleFollowIcon,
            leftImageView,
            leftIcon,
            rightImageView,
            rightIcon,
            clickListener
        )
    }

    fun setTitle(
        titleTextView: TextView,
        titleStr: String,
        titleFollowImageView: ImageView,
        @DrawableRes titleFollowIcon: Int?,
        leftImageView: ImageView,
        @DrawableRes leftIcon: Int?,
        rightImageView: ImageView,
        @DrawableRes rightIcon: Int?,
        clickListener: View.OnClickListener?
    ) {
        setTitle(
            titleTextView,
            titleStr,
            titleFollowImageView,
            titleFollowIcon,
            leftImageView,
            leftIcon,
            rightImageView,
            rightIcon,
            DEFAULT_WIDTH_SMALL,
            DEFAULT_HEIGHT_SMALL,
            DEFAULT_WIDTH_NORMAL,
            DEFAULT_HEIGHT_NORMAL,
            DEFAULT_WIDTH_NORMAL,
            DEFAULT_HEIGHT_NORMAL,
            clickListener
        )

    }

    fun setTitle(
        titleTextView: TextView,
        titleStr: String,
        titleFollowImageView: ImageView,
        @DrawableRes titleFollowIcon: Int?,
        leftImageView: ImageView,
        @DrawableRes leftIcon: Int?,
        rightImageView: ImageView,
        @DrawableRes rightIcon: Int?,
        titleFollowImageWidth: Float, titleFollowImageHeight: Float,
        leftImageWidth: Float, leftImageHeight: Float,
        rightImageWidth: Float, rightImageHeight: Float,
        clickListener: View.OnClickListener?
    ) {
        titleTextView.text = titleStr
        viewSetClickListener(titleTextView, clickListener)
        titleFollowIcon?.let {
            setImageView(titleFollowImageView, it, titleFollowImageWidth, titleFollowImageHeight)
            titleFollowImageView.visibility = View.VISIBLE
            viewSetClickListener(titleFollowImageView, clickListener)
        }
        leftIcon?.let {
            setImageView(leftImageView, it, leftImageWidth, leftImageHeight)
            leftImageView.visibility = View.VISIBLE
            viewSetClickListener(leftImageView, clickListener)
        }
        rightIcon?.let {
            setImageView(rightImageView, it, rightImageWidth, rightImageHeight)
            rightImageView.visibility = View.VISIBLE
            viewSetClickListener(rightImageView, clickListener)
        }
    }

    fun setImageView(image: ImageView, @DrawableRes id: Int) {
        setImageView(image, id, 24f, 24f)
    }

    /**
     * @param image
     * @param width 单位dp
     * @param height 单位dp
     */
    private fun setImageView(image: ImageView, @DrawableRes id: Int, width: Float, height: Float) {
        context?.let {
            val layoutParas = image.layoutParams as ConstraintLayout.LayoutParams
            layoutParas.width = ScreenUtil.dip2px(it, width)
            layoutParas.height = ScreenUtil.dip2px(it, height)
            image.setImageResource(id)
        }
    }
    private fun viewSetClickListener(view: View, listener: View.OnClickListener?) {
        listener?.let {
            view.setOnClickListener(it)
        }
    }

    fun getColor(@ColorRes id: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireContext().resources.getColor(id, null)
        } else {
            requireContext().resources.getColor(id)
        }
    }

    /**默认设置FragmentBackStatus.NONE， 需要设置成FragmentBackStatus.EXIT_APP时在相应Fragment重写该方法*/
    open fun setCurrentFragmentBackStatus() {
        BikeApplication.fragmentBackStatus = FragmentBackStatus.NONE
    }
}