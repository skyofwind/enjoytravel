package com.huishan.enjoytravel.ui.home.bikelist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.entity.ItemBikeListEntity
import com.huishan.enjoytravel.data.remote.entity.ItemDropDownCommon
import com.huishan.enjoytravel.databinding.FragmentBikeListBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.CommonDropDownViewModel
import com.huishan.enjoytravel.ui.MyBindingAdapter
import com.huishan.enjoytravel.util.LogUtil
import com.huishan.enjoytravel.widget.GridSpacingItemDecoration
import com.huishan.enjoytravel.widget.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint

/**车辆列表*/
@AndroidEntryPoint
class BikeListFragment : BaseFragment() {

    private var viewDataBinding: FragmentBikeListBinding? = null
    private val viewModel by viewModels<BikeListViewModel>()
    private val dropDownViewModel by viewModels<CommonDropDownViewModel>()
    var adapter: MyBindingAdapter<ItemBikeListEntity>? = null
    var dropAdapter: MyBindingAdapter<ItemDropDownCommon>? = null

    override fun getLayoutResId(): Int {
        return R.layout.fragment_bike_list
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentBikeListBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initTitleBar()
        initList()
        observeLiveData()
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "我享出行深圳服务区",
            R.drawable.ic_black_triangle_down,
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
            R.id.tv_title, R.id.iv_title_icon -> {
                findNavController().navigate(R.id.service_select_fragment_dest)
            }
            /* R.id.iv_right -> {
                 showPopWindow()
             }*/
        }
    }

    fun initList() {
        if (adapter == null) {
            adapter = MyBindingAdapter(
                requireContext(),
                R.layout.item_bike_status,
                viewModel,
                viewModel.entityList
            )
            viewDataBinding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            viewDataBinding!!.recyclerView.addItemDecoration(SpacesItemDecoration(0, 40, 0, 0))
            viewDataBinding!!.recyclerView.adapter = adapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeLiveData() {
        viewModel.distanceType.observe(this) {
            when (it) {
                0 -> {
                    viewDataBinding!!.ivDistance.setImageResource(R.drawable.ic_filter_none)
                }
                1 -> {
                    viewDataBinding!!.ivDistance.setImageResource(R.drawable.ic_filter_high)
                }
                2 -> {
                    viewDataBinding!!.ivDistance.setImageResource(R.drawable.ic_filter_low)
                }
            }
        }
        viewModel.batteryPowerType.observe(this) {
            when (it) {
                0 -> {
                    viewDataBinding!!.ivPower.setImageResource(R.drawable.ic_filter_none)
                }
                1 -> {
                    viewDataBinding!!.ivPower.setImageResource(R.drawable.ic_filter_high)
                }
                2 -> {
                    viewDataBinding!!.ivPower.setImageResource(R.drawable.ic_filter_low)
                }
            }
        }
        viewModel.showDrop.observe(this) { type ->
            when (type) {
                1 -> {
                    initDropList(viewModel.allStatusList!!)
                }
                2 -> {
                    initDropList(viewModel.allPowerList!!)
                }
            }
        }
        dropDownViewModel.targetItem.observe(this) { target ->
            target?.let {
                dropAdapter!!.notifyDataSetChanged()
                filterByTarget(it)
            }
        }
    }

    private fun initDropList(list: List<ItemDropDownCommon>) {
        dropDownViewModel.entityList = list
        if (dropAdapter == null) {
            dropAdapter = MyBindingAdapter(
                requireContext(),
                R.layout.item_drop_down_common,
                dropDownViewModel,
                dropDownViewModel.entityList
            )
            viewDataBinding!!.rvTypeContent.layoutManager = GridLayoutManager(context, 4)
            viewDataBinding!!.rvTypeContent.addItemDecoration(
                GridSpacingItemDecoration(
                    4,
                    30,
                    false
                )
            )
            viewDataBinding!!.rvTypeContent.adapter = dropAdapter
        } else {
            dropAdapter!!.resetList(dropDownViewModel.entityList)
        }
    }

    /**处理条件筛选，因为还不清楚接口数据有哪些写，大概根据是根据target.parent判断是“全部状态”还是“全部电量”，根据target.type去执行具体操作，
     * 如果后端接口没有这些相关的类型，那就需要前端自己赋值类型了
     */
    private fun filterByTarget(target: ItemDropDownCommon) {
        LogUtil.e("filterByTarget", target.toString())
    }
}