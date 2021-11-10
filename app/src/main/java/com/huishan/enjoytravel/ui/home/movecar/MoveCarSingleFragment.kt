package com.huishan.enjoytravel.ui.home.movecar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.databinding.FragmentMoveCarSingleBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.DataShareViewModel

/**单台挪车*/
class MoveCarSingleFragment: BaseFragment() {

    private val dataShareViewModel by activityViewModels<DataShareViewModel>()
    private var viewDataBinding: FragmentMoveCarSingleBinding? = null
    private val viewModel by viewModels<MoveCarSingleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentMoveCarSingleBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_move_car_single
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
            "单台挪车",
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
            /*R.id.tv_title, R.id.iv_title_icon -> {
            }*/
            R.id.iv_right -> {
                /**单台挪车的规则流程，现在还不知道是什么，要问他们*/

            }
        }
    }
}