package com.huishan.enjoytravel.ui.home.bikelist

import android.text.Editable
import android.view.View

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.entity.ItemBikeListEntity
import com.huishan.enjoytravel.data.remote.entity.ItemDropDownCommon
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**可以再layout中查看，databinding使用了那些数据控制视图*/
@HiltViewModel
class BikeListViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {
    var entityList = ArrayList<ItemBikeListEntity>()

    val totalNumber = MutableLiveData<Int>(0)

    /**记录“全部状态”点击状态*/
    private val _allStatusType = MutableLiveData<Boolean>(false)
    val allStatusType: LiveData<Boolean> = _allStatusType

    /**记录"全部电量"*/
    private val _allBatteryPowerType = MutableLiveData<Boolean>(false)
    val allBatteryPowerType: LiveData<Boolean> = _allBatteryPowerType

    private val _distanceType = MutableLiveData<Int>(0)
    val distanceType: LiveData<Int> = _distanceType

    private val _batteryPowerType = MutableLiveData<Int>(0)
    val batteryPowerType: LiveData<Int> = _batteryPowerType

    /**控制rv_type_content是否显示的LiveData*/
    val typeContentVisible = MutableLiveData<Boolean>(false)
    /**控制mask是否显示的LiveData*/
    val maskVisible = MutableLiveData<Boolean>(false)
    /**“全部状态”相关显示列表*/
    var allStatusList: List<ItemDropDownCommon>? = null
    /**“全部电量”相关显示列表*/
    var allPowerList: List<ItemDropDownCommon>? = null

    /**通知下拉显示,1 = "全部状态", 2 = "全部电量"*/
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
        allStatusList = a
        allPowerList = b
    }

    fun onBikeNumberChange(s: Editable?) {

    }

    fun onAllStatusClick() {
        if (_allStatusType.value == false) {
            if (_allBatteryPowerType.value == true) {
                _allBatteryPowerType.value = false
            }
            _allStatusType.value = true
            typeContentVisible.value = true
            maskVisible.value = true

            _showDrop.value = 1
        } else {
            _allStatusType.value = false
            typeContentVisible.value = false
            maskVisible.value = false
        }
    }

    fun onAllBatteryPowerClick() {
        if (_allBatteryPowerType.value == false) {
            if (_allStatusType.value == true) {
                _allStatusType.value = false
            }
            _allBatteryPowerType.value = true
            typeContentVisible.value = true
            maskVisible.value = true

            _showDrop.value = 2
        } else {
            _allBatteryPowerType.value = false
            typeContentVisible.value = false
            maskVisible.value = false
        }
    }

    fun onDistanceFilter() {
        when (_distanceType.value) {
            0 -> {
                _distanceType.value = 1
            }
            1 -> {
                _distanceType.value = 2
            }
            2 -> {
                _distanceType.value = 1
            }
        }
    }

    fun onBatteryFilter() {
        when (_batteryPowerType.value) {
            0 -> {
                _batteryPowerType.value = 1
            }
            1 -> {
                _batteryPowerType.value = 2
            }
            2 -> {
                _batteryPowerType.value = 1
            }
        }
    }

    fun onMaskOnClick() {
        typeContentVisible.value = false
        maskVisible.value = false
        _allStatusType.value = false
        _allBatteryPowerType.value = false
    }

    fun gotoBikeDetail(view: View, itemBikeList: ItemBikeListEntity) {
        view.findNavController().navigate(R.id.bike_detail_fragment_dest)
    }

}