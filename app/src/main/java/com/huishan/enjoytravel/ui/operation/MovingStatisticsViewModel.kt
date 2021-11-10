package com.huishan.enjoytravel.ui.operation


import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huishan.enjoytravel.data.remote.entity.ItemDropDownCommon
import com.huishan.enjoytravel.data.remote.entity.MovingStatisticsEntity
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovingStatisticsViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {
    var entityList = ArrayList<MovingStatisticsEntity>()

    var startTime: String = ""
    var endTime: String = ""

    /**记录“服务区”点击状态*/
    private val _allServiceType = MutableLiveData<Boolean>(false)
    val allServiceType: LiveData<Boolean> = _allServiceType

    /**记录"挪车类型"*/
    private val _allMoveCarType = MutableLiveData<Boolean>(false)
    val allMoveCarType: LiveData<Boolean> = _allMoveCarType

    /**控制rv_type_content是否显示的LiveData*/
    val typeContentVisible = MutableLiveData<Boolean>(false)

    /**控制mask是否显示的LiveData*/
    val maskVisible = MutableLiveData<Boolean>(false)

    /**“服务区”相关显示列表*/
    var allServiceList: List<ItemDropDownCommon>? = null

    /**“挪车类型”相关显示列表*/
    var allMoveCarList: List<ItemDropDownCommon>? = null

    /**通知下拉显示,1 = "服务区", 2 = "挪车类型"*/
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
        allServiceList = a
        allMoveCarList = b
    }

    fun onAllServiceClick() {
        if (_allServiceType.value == false) {
            if (_allMoveCarType.value == true) {
                _allMoveCarType.value = false
            }
            _allServiceType.value = true
            typeContentVisible.value = true
            maskVisible.value = true

            _showDrop.value = 1
        } else {
            _allServiceType.value = false
            typeContentVisible.value = false
            maskVisible.value = false
        }
    }

    fun onAllMoveCarClick() {
        if (_allMoveCarType.value == false) {
            if (_allServiceType.value == true) {
                _allServiceType.value = false
            }
            _allMoveCarType.value = true
            typeContentVisible.value = true
            maskVisible.value = true

            _showDrop.value = 2
        } else {
            _allMoveCarType.value = false
            typeContentVisible.value = false
            maskVisible.value = false
        }
    }

    fun onMaskOnClick() {
        typeContentVisible.value = false
        maskVisible.value = false
        _allServiceType.value = false
        _allMoveCarType.value = false
    }

    fun onBikeNumberChange(s: Editable?) {

    }
}