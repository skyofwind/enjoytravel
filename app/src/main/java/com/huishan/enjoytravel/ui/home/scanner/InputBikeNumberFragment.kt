package com.huishan.enjoytravel.ui.home.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.databinding.FragmentInputBikeNumberBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.DataShareViewModel
import com.huishan.enjoytravel.widget.splitedittext.OnInputListener
import dagger.hilt.android.AndroidEntryPoint

/**手动输入车辆号*/
@AndroidEntryPoint
class InputBikeNumberFragment : BaseFragment() {

    private var viewDataBinding: FragmentInputBikeNumberBinding? = null
    private val viewModel by viewModels<InputBikeNumberViewModel>()
    private val dataShareViewModel by activityViewModels<DataShareViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentInputBikeNumberBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_input_bike_number
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initTitleBar()
        viewDataBinding!!.etBike.setOnInputListener(object : OnInputListener() {
            override fun onInputFinished(content: String?) {

            }
        })
        viewModel.confirm.observe(this) { isConfirm ->
            isConfirm?.let {
                dataShareViewModel.resetBikeNumber( viewModel.bikeNumber.toString())
                requireActivity().onBackPressed()
            }
        }
        viewModel.failure.observe(this) { msg ->
            msg?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        }
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "手动输入车辆号",
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
            /*R.id.tv_title, R.id.iv_title_icon -> {
            }*/
            /*R.id.iv_right -> {

            }*/
        }
    }
}