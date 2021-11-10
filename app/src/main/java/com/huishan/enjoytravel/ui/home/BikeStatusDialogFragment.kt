package com.huishan.enjoytravel.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.entity.ItemPopBikeStatusEntity
import com.huishan.enjoytravel.databinding.PopwindowBikeStatusBinding
import com.huishan.enjoytravel.ui.MyBindingAdapter
import com.huishan.enjoytravel.util.DisplayUtil
import com.huishan.enjoytravel.util.ScreenUtil
import com.huishan.enjoytravel.widget.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BikeStatusDialogFragment : DialogFragment() {

    private var viewDataBinding: PopwindowBikeStatusBinding? = null
    private var viewModel: HomeViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initWindow()
        val view = inflater.inflate(R.layout.popwindow_bike_status, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewDataBinding = PopwindowBikeStatusBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    override fun onStart() {
        super.onStart()
        val win = dialog!!.window!!
        val params = win.attributes
        params.gravity = Gravity.RIGHT or Gravity.BOTTOM
        params.width =
            DisplayUtil.getScreenWidth(requireActivity()) - ScreenUtil.dip2px(requireContext(), 50f)
        params.height = DisplayUtil.getContentHeight(requireActivity()) - DisplayUtil.getStatusBarHeight(requireActivity())
        params.y = DisplayUtil.getStatusBarHeight(requireActivity())
        params.windowAnimations = R.style.pop_anim_style
        win.attributes = params
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

    fun initList() {
        viewDataBinding!!.recyclerView.layoutManager = GridLayoutManager(context, 3)
        viewDataBinding!!.recyclerView.addItemDecoration(GridSpacingItemDecoration(3, 50, false))
        val adapter = MyBindingAdapter<ItemPopBikeStatusEntity>(
            requireContext(),
            R.layout.item_pop_bike_status,
            viewModel!!,
            viewModel!!.entityList
        )
        viewDataBinding!!.recyclerView.adapter = adapter
    }

    fun initWindow() {
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCanceledOnTouchOutside(true)

    }
}