package com.huishan.enjoytravel.ui.home.changebattery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.databinding.FragmentChangeBatteryBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.DataShareViewModel
import dagger.hilt.android.AndroidEntryPoint

/**换电池*/
@AndroidEntryPoint
class ChangeBatteryFragment: BaseFragment() {

    private val viewModel by viewModels<ChangeBatteryViewModel>()
    private var viewDataBinding: FragmentChangeBatteryBinding? = null
    private val dataShareViewModel by activityViewModels<DataShareViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentChangeBatteryBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_change_battery
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
            "换电池",
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