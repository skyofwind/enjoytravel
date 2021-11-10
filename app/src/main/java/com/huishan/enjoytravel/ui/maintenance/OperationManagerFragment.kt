package com.huishan.enjoytravel.ui.maintenance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.huishan.enjoytravel.BikeApplication
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.databinding.FragmentOperationManagerBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.FragmentBackStatus
import dagger.hilt.android.AndroidEntryPoint

/**运维管理*/
@AndroidEntryPoint
class OperationManagerFragment: BaseFragment() {

    private var viewDataBinding: FragmentOperationManagerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentOperationManagerBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_operation_manager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initTitleBar()
        viewDataBinding!!.powerThresholdSet.setOnClickListener(onClickListener)
        viewDataBinding!!.bikeDetect.setOnClickListener(onClickListener)
        viewDataBinding!!.ivPowerThresholdSet.setOnClickListener(onClickListener)
        viewDataBinding!!.ivBikeDetect.setOnClickListener(onClickListener)
        viewDataBinding!!.tvPowerThresholdSet.setOnClickListener(onClickListener)
        viewDataBinding!!.tvBikeDetect.setOnClickListener(onClickListener)
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "运维管理",
            null,
            null,
            null,
            null
        )
    }

    private val onClickListener = View.OnClickListener {
        when (it.id) {
            /*R.id.iv_left -> {
                requireActivity().onBackPressed()
            }
            R.id.tv_title, R.id.iv_title_icon -> {
            }
            R.id.iv_right -> {

            }*/
            R.id.power_threshold_set, R.id.iv_power_threshold_set, R.id.tv_power_threshold_set -> {
                it.findNavController().navigate(R.id.power_threshold_set_fragment_dest)
            }
            R.id.bike_detect, R.id.iv_bike_detect, R.id.tv_bike_detect -> {
                it.findNavController().navigate(R.id.bike_detect_fragment_dest)
            }
        }
    }

    override fun setCurrentFragmentBackStatus() {
        BikeApplication.fragmentBackStatus = FragmentBackStatus.EXIT_APP
    }
}