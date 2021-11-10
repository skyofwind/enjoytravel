package com.huishan.enjoytravel.ui.home.inspectionworkorder

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.entity.ItemDropDownCommon
import com.huishan.enjoytravel.data.remote.entity.ItemInspectionWorkOrderEntity
import com.huishan.enjoytravel.databinding.FragmentInspectionWorkOrderBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.CommonDropDownViewModel
import com.huishan.enjoytravel.ui.MyBindingAdapter
import com.huishan.enjoytravel.util.LogUtil
import com.huishan.enjoytravel.util.TimeUtil
import com.huishan.enjoytravel.widget.GridSpacingItemDecoration
import com.huishan.enjoytravel.widget.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/**巡检工单*/
@AndroidEntryPoint
class InspectionWorkOrderFragment: BaseFragment() {

    private val viewModel by viewModels<InspectionWorkOrderViewModel>()
    private var viewDataBinding: FragmentInspectionWorkOrderBinding? = null
    private var adapter: MyBindingAdapter<ItemInspectionWorkOrderEntity>? = null
    private val dropDownViewModel by viewModels<CommonDropDownViewModel>()
    private var dropAdapter: MyBindingAdapter<ItemDropDownCommon>? = null

    /**起始时间 */
    private var mStartDate: Calendar? = null
    /**结束时间 */
    private var mEndDate: Calendar? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentInspectionWorkOrderBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_inspection_work_order
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
        initCalendar()
        viewDataBinding!!.tvStartDate.setOnClickListener(onClickListener)
        viewDataBinding!!.tvEndDate.setOnClickListener(onClickListener)
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "我享出行深圳服务区",
            R.drawable.ic_black_triangle_down,
            R.drawable.ic_arrow_left,
            R.drawable.ic_detail_2,
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
            R.id.iv_right -> {
                findNavController().navigate(R.id.bike_repair_fragment_dest)
            }
            R.id.tv_start_date -> {
                selectStartTime()
            }
            R.id.tv_end_date -> {
                selectEndTime()
            }
        }
    }

    fun initList() {
        if (adapter == null) {
            adapter = MyBindingAdapter(requireContext(), R.layout.item_inspection_work_order, viewModel, viewModel.entityList)
            viewDataBinding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            viewDataBinding!!.recyclerView.addItemDecoration(SpacesItemDecoration(0, 40, 0, 0))
            viewDataBinding!!.recyclerView.adapter = adapter
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

    @SuppressLint("NotifyDataSetChanged")
    private fun observeLiveData() {
        viewModel.showDrop.observe(this) { type ->
            when (type) {
                1 -> {
                    initDropList(viewModel.allFaultList!!)
                }
                2 -> {
                    initDropList(viewModel.allOrderList!!)
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

    private fun initCalendar() {
        if (mStartDate == null) {
            mStartDate = Calendar.getInstance()
            mStartDate!!.add(Calendar.DAY_OF_MONTH, -7)
        }
        if (mEndDate == null) {
            mEndDate = Calendar.getInstance()
            mEndDate!!.add(Calendar.DAY_OF_MONTH, 0)
        }
    }

    fun selectStartTime() {
        val dialog = DatePickerDialog(
            requireContext(), object : DatePickerDialog.OnDateSetListener {
                var isFirst = false
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    if (!isFirst) {
                        isFirst = true
                    } else {
                        return
                    }

                    val tempDate = Calendar.getInstance()
                    tempDate.set(year, month, dayOfMonth)
                    if (TimeUtil.compareTime(tempDate, mEndDate) > 0) {
                        if (tempDate.get(Calendar.YEAR) != mEndDate?.get(Calendar.YEAR)
                            || tempDate.get(Calendar.MONTH) != mEndDate?.get(Calendar.MONTH)
                            || tempDate.get(Calendar.DAY_OF_MONTH) != mEndDate?.get(Calendar.DAY_OF_MONTH)
                        ) {
                            Toast.makeText(
                                requireContext().applicationContext,
                                getString(R.string.text_time_beyond),
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }

                    }
                    // 更新日期
                    mStartDate!!.set(year, month, dayOfMonth)
                    // 刷新UI
                    viewModel.startTime = TimeUtil.getFormatDateTime(mStartDate, TimeUtil.FORMAT_DATE)
                    viewDataBinding!!.tvStartDate.text = viewModel.startTime
                    /**此处执行时间筛选动作*/

                }

            },
            mStartDate!![Calendar.YEAR],
            mStartDate!![Calendar.MONTH],
            mStartDate!![Calendar.DAY_OF_MONTH]
        )
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun selectEndTime() {
        val dialog = DatePickerDialog(
            context!!, object : DatePickerDialog.OnDateSetListener {
                var isFirst = false
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    if (!isFirst) {
                        isFirst = true
                    } else {
                        return
                    }

                    val tempDate = Calendar.getInstance()
                    tempDate.set(year, month, dayOfMonth)
                    if (TimeUtil.compareTime(tempDate, mStartDate) < 0) {
                        if (tempDate.get(Calendar.YEAR) != mEndDate?.get(Calendar.YEAR)
                            || tempDate.get(Calendar.MONTH) != mEndDate?.get(Calendar.MONTH)
                            || tempDate.get(Calendar.DAY_OF_MONTH) != mEndDate?.get(Calendar.DAY_OF_MONTH)
                        ) {
                            Toast.makeText(
                                requireContext().applicationContext,
                                getString(R.string.text_time_beyond),
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }
                    }
                    // 更新日期
                    mEndDate!!.set(year, month, dayOfMonth)
                    // 刷新UI
                    viewModel.endTime = TimeUtil.getFormatDateTime(mEndDate, TimeUtil.FORMAT_DATE)
                    viewDataBinding!!.tvEndDate.text = viewModel.endTime
                    /**此处执行时间筛选动作*/

                }

            },
            mEndDate!![Calendar.YEAR],
            mEndDate!![Calendar.MONTH],
            mEndDate!![Calendar.DAY_OF_MONTH]
        )
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}