package com.huishan.enjoytravel.ui.home.scanner

import android.view.View

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.bluetooth.BlueToothUtil
import com.huishan.enjoytravel.bluetooth.BluetoothConstant
import com.huishan.enjoytravel.bluetooth.model.BleLockRearWheelModel
import com.huishan.enjoytravel.bluetooth.model.BleUnlockRearWheelModel
import com.huishan.enjoytravel.data.repository.Repository
import com.huishan.enjoytravel.ui.BlueToothViewModel
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 应该是优先调用后端接口控制电动车的，需求文档都没有，车辆编号又不是MAC地址，
 * 用车辆编号是不能直连蓝牙的，应该只能通过后端接口获取蓝牙状态，只能通过蓝牙进行的操作才需要进行蓝牙连接（如寻车），
 * 但是后端接口也是基本还没完成，现在写逻辑也很麻烦，后面接口出来，自己再根据需求理解去更改吧
 */
@HiltViewModel
class ScannerViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {

    private val _bikeNumber = UnPeekLiveData<String>()
    val bikeNumber: LiveData<String> = _bikeNumber

    private val _failure = MutableLiveData<String>()
    val failure: LiveData<String> = _failure

    private val _flashOpen = MutableLiveData<Boolean>(false)
    val flashOpen: LiveData<Boolean> = _flashOpen

    private val _scanType = MutableLiveData<ScanType>(ScanType.OPEN)
    val scanType: MutableLiveData<ScanType> = _scanType

    fun setBikeNumber(bikeNumber: String) {
        _bikeNumber.value = bikeNumber
    }

    fun flashChange(isChecked: Boolean) {
        _flashOpen.value = isChecked
    }

    fun gotoEditor(view: View) {
        view.findNavController().navigate(R.id.input_bike_number_fragment_dest)
    }

    fun scanTypeChange(view: View, isChecked: Boolean, type: ScanType, blueToothViewModel: BlueToothViewModel) {
        if (isChecked) {
            _scanType.value = type
            writeSomething(view, _scanType.value, blueToothViewModel)
        }
    }

    /**这里后面要修改，什么时候调用后端接口执行操作，什么时候通过蓝牙进行操作*/
    fun writeSomething(view: View, type: ScanType?, blueToothViewModel: BlueToothViewModel) {
        when (type) {
            ScanType.OPEN -> {
                /*blueToothViewModel.writeBlueTooth(
                    BlueToothUtil.getByteUnlockRearWheel(
                        BleUnlockRearWheelModel(
                            BluetoothConstant.Voice.VOICE_DISARM,
                            50
                        )
                    )
                )*/
            }
            ScanType.LOCK -> {
                /*blueToothViewModel.writeBlueTooth(
                    BlueToothUtil.getByteLockRearWheel(
                        BleLockRearWheelModel(
                            BluetoothConstant.Voice.VOICE_DISARM,
                            50
                        )
                    )
                )*/
            }
            ScanType.DETAIL -> {
                if (!_bikeNumber.value.isNullOrEmpty()) {
                    view.findNavController().navigate(R.id.bike_detail_fragment_dest)
                }
            }
        }
    }

    enum class ScanType {
        DETAIL,
        OPEN,
        LOCK
    }

}