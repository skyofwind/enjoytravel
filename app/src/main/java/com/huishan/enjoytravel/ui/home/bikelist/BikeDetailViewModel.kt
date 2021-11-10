package com.huishan.enjoytravel.ui.home.bikelist

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.bluetooth.BlueToothUtil
import com.huishan.enjoytravel.bluetooth.BluetoothConstant
import com.huishan.enjoytravel.bluetooth.model.BlePlayVoiceModel
import com.huishan.enjoytravel.bluetooth.model.BleUnlockRearWheelModel
import com.huishan.enjoytravel.data.repository.Repository
import com.huishan.enjoytravel.ui.BlueToothViewModel
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BikeDetailViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {

    private val _failure = UnPeekLiveData<String>()
    val failure: LiveData<String> = _failure

    //开锁，不清楚要调用后车轮开锁命令还是调用启动命令，暂时先用后车轮开锁
    fun onUnlock(viewModel: BlueToothViewModel) {
        if (viewModel.isConnect()) {
            val bytes = BlueToothUtil.getByteUnlockRearWheel(BleUnlockRearWheelModel(BluetoothConstant.Voice.VOICE_DISARM, 50))
            viewModel.writeBlueTooth(bytes)
        } else {
            _failure.value = "电动车蓝牙还未连接上"
        }
    }
    //关锁，不清楚要调用后车轮关锁命令还是调用设防命令，暂时先用后车轮关锁
    fun onLock(viewModel: BlueToothViewModel) {
        if (viewModel.isConnect()) {
            val bytes = BlueToothUtil.getByteUnlockRearWheel(BleUnlockRearWheelModel(BluetoothConstant.Voice.VOICE_DISARM, 50))
            viewModel.writeBlueTooth(bytes)
        } else {
            _failure.value = "电动车蓝牙还未连接上"
        }
    }

    fun onPlayVoiceFind(viewModel: BlueToothViewModel) {
        if (viewModel.isConnect()) {
            val bytes = BlueToothUtil.getBytePlayVoice(BlePlayVoiceModel(BluetoothConstant.Voice.VOICE_CAR_SEARCH, 100))
            viewModel.writeBlueTooth(bytes)
        } else {
            _failure.value = "电动车蓝牙还未连接上"
        }
    }

    fun onChangeBattery(view: View, viewModel: BlueToothViewModel) {
        if (viewModel.isConnect()) {
            view.findNavController().navigate(R.id.change_battery_fragment_dest)
        } else {
            _failure.value = "电动车蓝牙还未连接上"
        }
    }

    fun onMoveCar(view: View, viewModel: BlueToothViewModel) {
        if (viewModel.isConnect()) {
            view.findNavController().navigate(R.id.move_car_fragment_dest)
        } else {
            _failure.value = "电动车蓝牙还未连接上"
        }
    }

    fun onReportFix(view: View, viewModel: BlueToothViewModel) {
        if (viewModel.isConnect()) {
            view.findNavController().navigate(R.id.bike_repair_fragment_dest)
        } else {
            _failure.value = "电动车蓝牙还未连接上"
        }
    }

    fun gotoHistoryOrderRecord(view: View) {
        view.findNavController().navigate(R.id.history_order_record_fragment_dest)
    }

    fun onLocation() {

    }
}