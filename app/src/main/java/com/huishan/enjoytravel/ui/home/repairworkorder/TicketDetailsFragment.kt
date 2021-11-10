package com.huishan.enjoytravel.ui.home.repairworkorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.databinding.FragmentRepairWorkOrderBinding
import com.huishan.enjoytravel.databinding.FragmentTicketDetailsBinding
import com.huishan.enjoytravel.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**车辆工单详情*/
@AndroidEntryPoint
class TicketDetailsFragment: BaseFragment() {

    private val viewModel by viewModels<TicketDetailsViewModel>()
    private var viewDataBinding: FragmentTicketDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentTicketDetailsBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_ticket_details
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
            "工单详情",
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