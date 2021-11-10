package com.huishan.enjoytravel.ui.home

import android.view.View

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.doFailure
import com.huishan.enjoytravel.data.remote.doSuccess
import com.huishan.enjoytravel.data.remote.entity.CurrentServiceEntity
import com.huishan.enjoytravel.data.remote.entity.ItemPopBikeStatusEntity
import com.huishan.enjoytravel.data.remote.entity.ServiceListEntity
import com.huishan.enjoytravel.data.remote.entity.VehicleListEntity
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {

    var isFirst = true

    var entityList = ArrayList<ItemPopBikeStatusEntity>()

    val zoomIndex = 17f

    private val _progressVisible = MutableLiveData<Boolean>(false)
    val progressVisible: LiveData<Boolean> = _progressVisible

    private val _mapType = MutableLiveData<Boolean>(false)
    val mapType: LiveData<Boolean> = _mapType

    private val _carCapacityVisible = MutableLiveData<Boolean>(false)
    val carCapacityVisible: LiveData<Boolean> = _carCapacityVisible

    private val _locationReset = MutableLiveData<Boolean>(false)
    val locationReset: LiveData<Boolean> = _locationReset

    private val _failure = MutableLiveData<String>()
    val failure: LiveData<String> = _failure

    private val _currentService = MutableLiveData<CurrentServiceEntity>()
    val currentService: LiveData<CurrentServiceEntity> = _currentService

    private val _serviceList = MutableLiveData<ServiceListEntity>()
    val serviceList: LiveData<ServiceListEntity> = _serviceList

    private val _vehicleList = MutableLiveData<VehicleListEntity>()
    val vehicleList:LiveData<VehicleListEntity> = _vehicleList

    val batteryPercentage = MutableLiveData<Int>(100)

    init {
        initEntityList()
    }

    private fun initEntityList() {
        if (entityList.isEmpty()) {
            for (i in 0..10) {
                entityList.add(ItemPopBikeStatusEntity())
            }
        }
    }

    fun filterBatteryChange(isChecked: Boolean) {
        _progressVisible.value = isChecked
    }

    fun switchMapChange(isChecked: Boolean) {
        _mapType.value = isChecked
    }

    fun carCapacityChange(isChecked: Boolean) {
        _carCapacityVisible.value = isChecked
    }

    fun locationResetChange(isChecked: Boolean) {
        _locationReset.value = isChecked
    }

    fun seekBarChange(progress: Int) {
        if (batteryPercentage.value == progress) {
            return
        }
        batteryPercentage.value = progress
    }

    fun gotoScanner(view: View) {
        view.findNavController().navigate(R.id.scanner_fragment_dest)
    }

    fun gotoMoveCar(view: View) {
        view.findNavController().navigate(R.id.move_car_fragment_dest)
    }

    fun gotoChangeBattery(view: View) {
        view.findNavController().navigate(R.id.change_battery_fragment_dest)
    }

    fun gotoRepairWorkOrder(view: View) {
        view.findNavController().navigate(R.id.repair_work_order_fragment_dest)
    }

    fun gotoInspectionWorkOrder(view: View) {
        view.findNavController().navigate(R.id.inspection_work_order_fragment_dest)
    }

    /**车辆状态弹窗筛选相关*/
    fun bikeStatusItemChange(popBikeStatusItemEntity: ItemPopBikeStatusEntity, isChecked: Boolean) {

    }

    fun filerCyclingChange(isChecked: Boolean) {

    }

    fun bikeStatusOnReset() {

    }

    fun bikeStatusConfirm() {

    }
    /**获取当前服务区*/
    fun getCurrentService(map: MutableMap<String, String>) {
        viewModelScope.launch(Dispatchers.IO) {
            bikeRepository.getCurrentService(map)
                .onStart {

                }
                .catch {

                }
                .onCompletion {

                }
                .collectLatest { result ->
                    result.doFailure { throwable ->
                        _failure.postValue(throwable?.message ?: "failure")
                    }
                    result.doSuccess { value ->
                        _currentService.postValue(value)
                    }
                }
        }
    }
    /**获取所有服务区列表*/
    fun getServiceList(map: MutableMap<String, String>) {
        viewModelScope.launch(Dispatchers.IO) {
            bikeRepository.getServiceList(map)
                .onStart {

                }
                .catch {

                }
                .onCompletion {

                }
                .collectLatest { result ->
                    result.doFailure { throwable ->
                        _failure.postValue(throwable?.message ?: "failure")
                    }
                    result.doSuccess { value ->
                        _serviceList.postValue(value)
                    }
                }
        }
    }

    /**获取车辆列表*/
    fun getVehicleList(map: MutableMap<String, String>) {
        viewModelScope.launch(Dispatchers.IO) {
            bikeRepository.getVehicleList(map)
                .onStart {

                }
                .catch {

                }
                .onCompletion {

                }
                .collectLatest { result ->
                    result.doFailure { throwable ->
                        _failure.postValue(throwable?.message ?: "failure")
                    }
                    result.doSuccess { value ->
                        _vehicleList.postValue(value)
                    }
                }
        }
    }
}