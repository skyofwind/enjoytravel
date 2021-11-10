package com.huishan.enjoytravel.ui.home.movecar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.entity.ItemMoveCarEntity
import com.huishan.enjoytravel.databinding.FragmentMoveCarScanCodeBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.MyBindingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoveCarScanCodeFragment: BaseFragment() {

    private var viewDataBinding: FragmentMoveCarScanCodeBinding? = null
    private val viewModel by activityViewModels<MoveCarBatchViewModel>()
    var adapter: MyBindingAdapter<ItemMoveCarEntity>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentMoveCarScanCodeBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_move_car_scan_code
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        if (adapter == null) {
            adapter = MyBindingAdapter(requireContext(), R.layout.item_move_car_scan_code, viewModel, viewModel.scanCodeList)
            viewDataBinding!!.rvList.layoutManager = LinearLayoutManager(requireContext())
            //viewDataBinding!!.rvList.addItemDecoration(SpacesItemDecoration(0, 40, 0, 0))
            viewDataBinding!!.rvList.adapter = adapter
        }
    }
}