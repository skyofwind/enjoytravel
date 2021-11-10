package com.huishan.enjoytravel.ui.home.movecar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.databinding.FragmentMoveCarBinding
import com.huishan.enjoytravel.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**挪车*/
@AndroidEntryPoint
class MoveCarFragment: BaseFragment() {

    private var viewDataBinding: FragmentMoveCarBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentMoveCarBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_move_car
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initTitleBar()
        viewDataBinding!!.moveCarBatchBg.setOnClickListener(onClickListener)
        viewDataBinding!!.moveCarSingleBg.setOnClickListener(onClickListener)
        viewDataBinding!!.ivMoveCarBatchBg.setOnClickListener(onClickListener)
        viewDataBinding!!.ivMoveCarSingleBg.setOnClickListener(onClickListener)
        viewDataBinding!!.tvMoveCarBatchBg.setOnClickListener(onClickListener)
        viewDataBinding!!.tvMoveCarSingleBg.setOnClickListener(onClickListener)
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "挪车",
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
            R.id.move_car_single_bg, R.id.iv_move_car_single_bg, R.id.tv_move_car_single_bg -> {
                it.findNavController().navigate(R.id.move_car_single_fragment_dest)
            }
            R.id.move_car_batch_bg, R.id.iv_move_car_batch_bg, R.id.tv_move_car_batch_bg -> {
                it.findNavController().navigate(R.id.move_car_batch_fragment_dest)
            }
        }
    }
}