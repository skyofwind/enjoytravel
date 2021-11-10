package com.huishan.enjoytravel.ui.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.huishan.enjoytravel.BikeApplication
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.databinding.FragmentPersonalBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.FragmentBackStatus
import dagger.hilt.android.AndroidEntryPoint

/**个人中心*/
@AndroidEntryPoint
class PersonalFragment: BaseFragment() {

    private var viewDataBinding: FragmentPersonalBinding? = null
    private val viewModel by activityViewModels<PersonalViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentPersonalBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_personal
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {

    }

    override fun setCurrentFragmentBackStatus() {
        BikeApplication.fragmentBackStatus = FragmentBackStatus.EXIT_APP
    }
}