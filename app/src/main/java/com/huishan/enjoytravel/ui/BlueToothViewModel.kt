package com.huishan.enjoytravel.ui

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGatt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleGattCallback
import com.clj.fastble.callback.BleNotifyCallback
import com.clj.fastble.callback.BleWriteCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.huishan.enjoytravel.bluetooth.BlueToothUtil
import com.huishan.enjoytravel.bluetooth.BluetoothConstant
import com.huishan.enjoytravel.bluetooth.model.BleConnectModel
import com.huishan.enjoytravel.bluetooth.model.BleLockRearWheelModel
import com.huishan.enjoytravel.bluetooth.model.BleUnlockRearWheelModel
import com.huishan.enjoytravel.bluetooth.response.BleResponse
import com.huishan.enjoytravel.ui.home.scanner.ScannerFragment
import com.huishan.enjoytravel.ui.home.scanner.ScannerViewModel
import com.huishan.enjoytravel.util.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 蓝牙操作统一使用，viewModel作用域应为Activity
 */
@HiltViewModel
class BlueToothViewModel @Inject constructor(/*private val bikeRepository: Repository*/) :
    ViewModel() {

    companion object {
        const val TAG = "BlueToothViewModel"
    }

    private val _failure = SingleLiveEvent<String>()
    val failure: LiveData<String> = _failure

    var mDevice: BleDevice? = null
    val _isConnect = SingleLiveEvent<BleConnectModel>()
    val isConnect: LiveData<BleConnectModel> = _isConnect

    /**后车轮解锁*/
    private val _unlockRearWheel = SingleLiveEvent<BleResponse>()
    val unlockRearWheel: LiveData<BleResponse> = _unlockRearWheel

    /**后车轮上锁*/
    private val _lockRearWheel = SingleLiveEvent<BleResponse>()
    val lockRearWheel: LiveData<BleResponse> = _lockRearWheel

    /**播放语音*/
    private val _playVoice = SingleLiveEvent<BleResponse>()
    val playVoice: LiveData<BleResponse> = _playVoice

    /**查询状态信息*/
    private val _queryStatusInformation = SingleLiveEvent<BleResponse>()
    val queryStatusInformation: LiveData<BleResponse> = _queryStatusInformation

    /**设防*/
    private val _fortify = SingleLiveEvent<BleResponse>()
    val fortify: LiveData<BleResponse> = _fortify

    /**启动*/
    private val _startUp = SingleLiveEvent<BleResponse>()
    val startUp: LiveData<BleResponse> = _startUp

    /**熄火*/
    private val _flameOut = SingleLiveEvent<BleResponse>()
    val flameOut: LiveData<BleResponse> = _flameOut

    /**关机*/
    private val _shutDown = SingleLiveEvent<BleResponse>()
    val shutDown: LiveData<BleResponse> = _shutDown

    /**重启*/
    private val _reboot = SingleLiveEvent<BleResponse>()
    val reboot: LiveData<BleResponse> = _reboot

    /**查询GPS信息*/
    private val _queryGps = SingleLiveEvent<BleResponse>()
    val queryGps: LiveData<BleResponse> = _queryGps

    /**查询设备IMSI*/
    private val _queryIMSI = SingleLiveEvent<BleResponse>()
    val queryIMSI: LiveData<BleResponse> = _queryIMSI

    /**电池仓锁解锁*/
    private val _unLockBatteryCompartmentLock = SingleLiveEvent<BleResponse>()
    val unLockBatteryCompartmentLock: LiveData<BleResponse> = _unLockBatteryCompartmentLock

    /**电池仓锁上锁*/
    private val _lockBatteryCompartment = SingleLiveEvent<BleResponse>()
    val lockBatteryCompartment: LiveData<BleResponse> = _lockBatteryCompartment

    /**查询网络状态*/
    private val _queryNetworkStatus = SingleLiveEvent<BleResponse>()
    val queryNetworkStatus: LiveData<BleResponse> = _queryNetworkStatus

    /**头盔锁解锁*/
    private val _helmetLockUnlock = SingleLiveEvent<BleResponse>()
    val helmetLockUnlock: LiveData<BleResponse> = _helmetLockUnlock

    /**打开电池锁*/
    private val _openBatteryLock = SingleLiveEvent<BleResponse>()
    val openBatteryLock: LiveData<BleResponse> = _openBatteryLock


    fun isConnect(): Boolean {
        if (mDevice != null && _isConnect.value!!.isConnect) {
            return true
        }

        return false
    }

    /**
     * 连接蓝牙操作，连接后自动发起notify
     * @param address mac地址
     * 重连操作可能会有问题，要注意下
     */
    fun connectBlueTooth(address: String?, isReconnect: Boolean) {
        if (isConnect() && mDevice!!.mac == address) {
            _failure.postValue("当前设备已连接")
            return
        }
        if (mDevice != null) {
            BleManager.getInstance().disconnect(mDevice)
            Thread.sleep(200)
        }

        val blueToothDevice = BleManager.getInstance().bluetoothAdapter.getRemoteDevice(address)
        mDevice = BleDevice(blueToothDevice)
        BleManager.getInstance().connect(
            mDevice,
            object : BleGattCallback() {
                @SuppressLint("LogUtilNotTimber")
                override fun onStartConnect() {
                    LogUtil.e(TAG, "onStartConnect")
                }

                @SuppressLint("LogUtilNotTimber")
                override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
                    LogUtil.e(TAG, "isConnect send onConnectFail, ${exception.toString()}")
                    _failure.postValue("设备连接失败")
                    _isConnect.postValue(BleConnectModel(false, address.toString(), isReconnect))
                }

                @SuppressLint("LogUtilNotTimber")
                override fun onConnectSuccess(
                    bleDevice: BleDevice?,
                    gatt: BluetoothGatt?,
                    status: Int
                ) {
                    LogUtil.e(TAG, "isConnect send onConnectSuccess, status = $status")
                    _isConnect.postValue(BleConnectModel(true, bleDevice?.mac, isReconnect))
                    Thread.sleep(100)
                    notifyBlueTooth()
                    Thread.sleep(100)
                }

                @SuppressLint("LogUtilNotTimber")
                override fun onDisConnected(
                    isActiveDisConnected: Boolean,
                    device: BleDevice?,
                    gatt: BluetoothGatt?,
                    status: Int
                ) {
                    LogUtil.e(
                        TAG,
                        "isConnect send onDisConnected, isActiveDisConnected = ${isActiveDisConnected}, status = $status"
                    )
                    _isConnect.postValue(BleConnectModel(false, device?.mac, true))
                    if (!isActiveDisConnected) {
                        Thread.sleep(100)
                        connectBlueTooth(address, true)
                        Thread.sleep(100)
                    }
                }

            })
    }

    /**订阅解析蓝牙返回的数据，并根据命令更新到指定的LiveData*/
    fun notifyBlueTooth() {
        BleManager.getInstance().notify(mDevice, BluetoothConstant.UUID.server_uuid,
            BluetoothConstant.UUID.server_tx_uuid, object : BleNotifyCallback() {
                override fun onNotifySuccess() {
                    LogUtil.e(TAG, "onNotifySuccess")
                }

                override fun onNotifyFailure(exception: BleException?) {
                    LogUtil.e(
                        TAG,
                        "onNotifyFailure exception = ${exception.toString()}"
                    )
                }

                override fun onCharacteristicChanged(data: ByteArray?) {
                    LogUtil.e(TAG, "onCharacteristicChanged")
                    data?.let {
                        confirmResult(BlueToothUtil.receive(it))
                    }
                }

            })
    }

    /**执行蓝牙写入操作*/
    fun writeBlueTooth(data: ByteArray) {
        if (isConnect()) {
            BleManager.getInstance().write(
                mDevice,
                BluetoothConstant.UUID.server_uuid,
                BluetoothConstant.UUID.server_rx_uuid,
                data,
                object : BleWriteCallback() {
                    override fun onWriteSuccess(current: Int, total: Int, justWrite: ByteArray?) {
                        LogUtil.e(
                            TAG,
                            "onWriteSuccess current = $current, total = $current"
                        )
                    }

                    override fun onWriteFailure(exception: BleException?) {
                        LogUtil.e(
                            TAG,
                            "onWriteFailure exception = ${exception.toString()}"
                        )
                        _failure.postValue(exception.toString())
                    }

                })
            Thread.sleep(100)
        } else {
            _failure.value = "设备已断开蓝牙连接，请先连接"
        }
    }

    fun confirmResult(res: BleResponse?) {
        res?.let { data ->
            when (data.cmd) {
                /**后轮锁解锁*/
                BluetoothConstant.CMD.UNLOCK_REAR_WHEEL -> {
                    _unlockRearWheel.value = data
                }
                /**后轮锁上锁*/
                BluetoothConstant.CMD.LOCK_REAR_WHEEL -> {
                    _lockRearWheel.value = data
                }
                /**播放语音*/
                BluetoothConstant.CMD.PLAY_VOICE -> {
                    _playVoice.value = data
                }
                /**查询状态信息*/
                BluetoothConstant.CMD.QUERY_STATUS_INFORMATION -> {
                    _queryStatusInformation.value = data
                }
                /**设防*/
                BluetoothConstant.CMD.FORTIFY -> {
                    _fortify.value = data
                }
                /**启动*/
                BluetoothConstant.CMD.START_UP -> {
                    _startUp.value = data
                }
                /**熄火*/
                BluetoothConstant.CMD.FLAME_OUT -> {
                    _flameOut.value = data
                }
                /**关机*/
                BluetoothConstant.CMD.SHUT_DOWN -> {
                    _shutDown.value = data
                }
                /**重启*/
                BluetoothConstant.CMD.REBOOT -> {
                    _reboot.value = data
                }
                /**查询GPS信息*/
                BluetoothConstant.CMD.QUERY_GPS -> {
                    _queryGps.value = data
                }
                /**查询设备IMSI*/
                BluetoothConstant.CMD.QUERY_IMSI -> {
                    _queryIMSI.value = data
                }
                /**电池仓锁解锁*/
                BluetoothConstant.CMD.UNLOCK_BATTERY_COMPARTMENT_LOCK -> {
                    _unLockBatteryCompartmentLock.value = data
                }
                /**电池仓锁上锁*/
                BluetoothConstant.CMD.LOCK_BATTERY_COMPARTMENT -> {
                    _lockBatteryCompartment.value = data
                }
                /**查询网络状态*/
                BluetoothConstant.CMD.QUERY_NETWORK_STATUS -> {
                    _queryNetworkStatus.value = data
                }
                /**头盔锁解锁*/
                BluetoothConstant.CMD.HELMET_LOCK_UNLOCK -> {
                    _helmetLockUnlock.value = data
                }
                /**打开电池锁*/
                BluetoothConstant.CMD.OPEN_BATTERY_LOCK -> {
                    _openBatteryLock.value = data
                }
            }
        }

    }
}