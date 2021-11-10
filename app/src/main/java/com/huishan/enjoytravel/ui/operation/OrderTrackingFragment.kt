package com.huishan.enjoytravel.ui.operation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.databinding.FragmentOperationalStatisticsBinding
import com.huishan.enjoytravel.databinding.FragmentOrderTrackingBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.DataShareViewModel
import dagger.hilt.android.AndroidEntryPoint

/**订单查询*/
@AndroidEntryPoint
class OrderTrackingFragment: BaseFragment() {

    private var viewDataBinding: FragmentOrderTrackingBinding? = null
    private val viewModel by viewModels<OrderTrackingViewModel>()
    private val dataShareViewModel by activityViewModels<DataShareViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentOrderTrackingBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_order_tracking
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initTitleBar()
        if (dataShareViewModel.getBikeNumber().isNotEmpty()) {
            viewDataBinding!!.etBikeNumber.setText(dataShareViewModel.getBikeNumber())
            dataShareViewModel.resetBikeNumber("")
        }
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "订单查询",
            null,
            R.drawable.ic_arrow_left,
            null,
            onClickListener
        )
    }

    private val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.iv_left -> {
                requireActivity().onBackPressed()
            }
            R.id.tv_title, R.id.iv_title_icon -> {

            }
            /* R.id.iv_right -> {
                 showPopWindow()
             }*/
        }
    }
}