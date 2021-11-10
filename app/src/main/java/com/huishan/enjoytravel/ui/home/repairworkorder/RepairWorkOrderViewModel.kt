package com.huishan.enjoytravel.ui.home.repairworkorder


import android.text.Editable
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.entity.ItemDropDownCommon
import com.huishan.enjoytravel.data.remote.entity.ItemRepairWorkOrderEntity
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepairWorkOrderViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {
    var list = ArrayList<ItemRepairWorkOrderEntity>()

    val totalNumber = MutableLiveData<Int>(0)

    var startTime: String = ""
    var endTime: String = ""

    /**记录“故障类型”点击状态*/
    private val _allFaultType = MutableLiveData<Boolean>(false)
    val allFaultType: LiveData<Boolean> = _allFaultType

    /**记录"工单状态"*/
    private val _allOrderType = MutableLiveData<Boolean>(false)
    val allOrderType: LiveData<Boolean> = _allOrderType

    /**控制rv_type_content是否显示的LiveData*/
    val typeContentVisible = MutableLiveData<Boolean>(false)

    /**控制mask是否显示的LiveData*/
    val maskVisible = MutableLiveData<Boolean>(false)

    /**“故障类型”相关显示列表*/
    var allFaultList: List<ItemDropDownCommon>? = null

    /**“工单状态”相关显示列表*/
    var allOrderList: List<ItemDropDownCommon>? = null

    /**通知下拉显示,1 = "故障类型", 2 = "工单状态"*/
    private val _showDrop = MutableLiveData<Int>()
    val showDrop: LiveData<Int> = _showDrop

    /**测试数据，后面应该删掉*/
    init {
        val a = ArrayList<ItemDropDownCommon>()
        a.add(ItemDropDownCommon(0, "待租", 0, false))
        a.add(ItemDropDownCommon(0, "骑行", 1, false))
        a.add(ItemDropDownCommon(0, "临停", 2, false))
        a.add(ItemDropDownCommon(0, "凑数", 3, false))
        a.add(ItemDropDownCommon(0, "凑数", 4, false))
        a.add(ItemDropDownCommon(0, "凑数", 5, false))
        val b = ArrayList<ItemDropDownCommon>()
        b.add(ItemDropDownCommon(1, "电量", 0, false))
        b.add(ItemDropDownCommon(1, "电量", 1, false))
        b.add(ItemDropDownCommon(1, "电量", 2, false))
        b.add(ItemDropDownCommon(1, "电量", 3, false))
        b.add(ItemDropDownCommon(1, "电量", 4, false))
        b.add(ItemDropDownCommon(1, "电量", 5, false))
        allFaultList = a
        allOrderList = b
    }

    fun onAllFaultClick() {
        if (_allFaultType.value == false) {
            if (_allOrderType.value == true) {
                _allOrderType.value = false
            }
            _allFaultType.value = true
            typeContentVisible.value = true
            maskVisible.value = true

            _showDrop.value = 1
        } else {
            _allFaultType.value = false
            typeContentVisible.value = false
            maskVisible.value = false
        }
    }

    fun onAllOrderClick() {
        if (_allOrderType.value == false) {
            if (_allFaultType.value == true) {
                _allFaultType.value = false
            }
            _allOrderType.value = true
            typeContentVisible.value = true
            maskVisible.value = true

            _showDrop.value = 2
        } else {
            _allOrderType.value = false
            typeContentVisible.value = false
            maskVisible.value = false
        }
    }

    fun onMaskOnClick() {
        typeContentVisible.value = false
        maskVisible.value = false
        _allFaultType.value = false
        _allOrderType.value = false
    }

    fun onBikeNumberChange(s: Editable?) {

    }

    fun gotoDetail(view: View, item: ItemRepairWorkOrderEntity) {
        view.findNavController().navigate(R.id.ticket_details_fragment_dest)
    }
}