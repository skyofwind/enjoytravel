package com.huishan.enjoytravel.ui.operation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.huishan.enjoytravel.BikeApplication
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.databinding.FragmentOperationalStatisticsBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.FragmentBackStatus
import dagger.hilt.android.AndroidEntryPoint

/**运营统计*/
@AndroidEntryPoint
class OperationalStatisticsFragment: BaseFragment() {

    private var viewDataBinding: FragmentOperationalStatisticsBinding? = null
    private val viewModel by activityViewModels<OperationalStatisticsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentOperationalStatisticsBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_operational_statistics
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initTitleBar()
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "运营统计",
            null,
            null,
            null,
            null
        )
    }

    override fun setCurrentFragmentBackStatus() {
        BikeApplication.fragmentBackStatus = FragmentBackStatus.EXIT_APP
    }
}