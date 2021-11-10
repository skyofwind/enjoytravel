package com.huishan.enjoytravel.ui.home.movecar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.entity.ItemMoveCarEntity
import com.huishan.enjoytravel.databinding.FragmentMoveCarCompleteBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.MyBindingAdapter
import dagger.hilt.android.AndroidEntryPoint

/**完成挪车*/
@AndroidEntryPoint
class MoveCarCompleteFragment: BaseFragment() {

    private var viewDataBinding: FragmentMoveCarCompleteBinding? = null
    private val viewModel by activityViewModels<MoveCarBatchViewModel>()
    var adapter: MyBindingAdapter<ItemMoveCarEntity>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentMoveCarCompleteBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }
        return view
    }


    override fun getLayoutResId(): Int {
        return R.layout.fragment_move_car_complete
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initTitleBar()
        initList()
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "完成挪车",
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
            R.id.iv_right -> {

            }
        }
    }

    private fun initList() {
        if (adapter == null) {
            adapter = MyBindingAdapter(requireContext(), R.layout.item_move_car_complete, viewModel, viewModel.completeList)
            viewDataBinding!!.rvList.layoutManager = LinearLayoutManager(requireContext())
            //viewDataBinding!!.rvList.addItemDecoration(SpacesItemDecoration(0, 40, 0, 0))
            viewDataBinding!!.rvList.adapter = adapter
        }
    }

}