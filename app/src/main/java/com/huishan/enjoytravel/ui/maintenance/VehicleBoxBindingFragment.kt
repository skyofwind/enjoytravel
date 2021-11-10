package com.huishan.enjoytravel.ui.maintenance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.databinding.FragmentVehicleBoxBindingBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.DataShareViewModel
import dagger.hilt.android.AndroidEntryPoint

/**车辆盒子绑定*/
@AndroidEntryPoint
class VehicleBoxBindingFragment : BaseFragment() {

    private var viewDataBinding: FragmentVehicleBoxBindingBinding? = null
    private val viewModel by viewModels<VehicleBoxBindingViewModel>()
    private val dataShareViewModel by activityViewModels<DataShareViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentVehicleBoxBindingBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_vehicle_box_binding
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
        if (dataShareViewModel.getBoxNumber().isNotEmpty()) {
            viewDataBinding!!.etBoxNumber.setText(dataShareViewModel.getBoxNumber())
            dataShareViewModel.resetBoxNumber("")
        }
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "车辆盒子绑定",
            null,
            R.drawable.ic_arrow_left,
            R.drawable.ic_detail,
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
            R.id.iv_right -> {

            }
        }
    }
}