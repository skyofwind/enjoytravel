package com.huishan.enjoytravel.bluetooth

import android.text.TextUtils
import com.huishan.enjoytravel.bluetooth.model.*
import com.huishan.enjoytravel.bluetooth.response.*
import com.huishan.enjoytravel.util.LogUtil
import java.lang.StringBuilder
import java.util.*

class BlueToothUtil {
    companion object {
        const val TAG = "BlueToothUtil"

        fun convertImeiToAddr(var0: String): String? {
            return if (!TextUtils.isEmpty(var0) && var0.length == 15) {
                val var1 = StringBuilder("")
                var var2 = var0.length
                while (var2 > 0) {
                    if (var2 > 5) {
                        var1.append(var0.subSequence(var2 - 2, var2)).append(":")
                    } else {
                        if (var2 <= 3) {
                            break
                        }
                        var1.append(var0.subSequence(var2 - 2, var2))
                    }
                    var2 -= 2
                }
                var1.toString()
            } else {
                ""
            }
        }

        fun convertAddrToImei(var0: String): String? {
            return if (TextUtils.isEmpty(var0)) {
                ""
            } else {
                val var1 = var0.split(":").toTypedArray()
                val var2: List<*> = Arrays.asList(*var1)
                Collections.reverse(var2)
                val var3 = StringBuilder()
                val var4 = var2.iterator()
                while (var4.hasNext()) {
                    val var5 = var4.next() as String
                    var3.append(var5)
                }
                var3.toString()
            }
        }

        fun receive(bytes: ByteArray): BleResponse? {
            var bleResponse: BleResponse? = null
            if (bytes.size >= 4) {
                val cmd = bytes[0]
                val len = bytes[1].toInt()
                LogUtil.e(TAG, "receive cmd = ${cmd}, len = ${len}")
                val data = ByteArray(len)
                bytes.copyInto(data, 0, 2, 2 + len)

                when (cmd) {
                    /**重启*/
                    BluetoothConstant.CMD.REBOOT -> {
                        bleResponse = responseReboot(data)
                    }
                    /**播放语音*/
                    BluetoothConstant.CMD.PLAY_VOICE -> {
                        bleResponse = responsePlayVoice(data)
                    }
                    /**查询状态信息*/
                    BluetoothConstant.CMD.QUERY_STATUS_INFORMATION -> {
                        bleResponse = responseQueryStatusInformation(data)
                    }
                    /**设防*/
                    BluetoothConstant.CMD.FORTIFY -> {
                        bleResponse = responseFortify(data)
                    }
                    /**启动*/
                    BluetoothConstant.CMD.START_UP -> {
                        bleResponse = responseStartUp(data)
                    }
                    /**熄火*/
                    BluetoothConstant.CMD.FLAME_OUT -> {
                        bleResponse = responseFlameOut(data)
                    }
                    /**后轮锁解锁*/
                    BluetoothConstant.CMD.UNLOCK_REAR_WHEEL -> {
                        bleResponse = responseUnlockRearWheel(data)
                    }
                    /**后轮锁上锁*/
                    BluetoothConstant.CMD.LOCK_REAR_WHEEL -> {
                        bleResponse = responseLockRearWheel(data)
                    }
                    /**关机*/
                    BluetoothConstant.CMD.SHUT_DOWN -> {
                        bleResponse = responseShutDown(data)
                    }
                    /**查询GPS信息*/
                    BluetoothConstant.CMD.QUERY_GPS -> {
                        bleResponse = responseQueryGps(data)
                    }
                    /**查询设备IMSI*/
                    BluetoothConstant.CMD.QUERY_IMSI -> {
                        bleResponse = responseQueryIMSI(data)
                    }
                    /**电池仓锁解锁*/
                    BluetoothConstant.CMD.UNLOCK_BATTERY_COMPARTMENT_LOCK -> {
                        bleResponse = responseUnLockBatteryCompartmentLock(data)
                    }
                    /**电池仓锁上锁*/
                    BluetoothConstant.CMD.LOCK_BATTERY_COMPARTMENT -> {
                        bleResponse = responseLockBatteryCompartment(data)
                    }
                    /**配置测速参数*/
                    BluetoothConstant.CMD.CONFIGURE_SPEED_MEASUREMENT_PARAMETERS -> {
                        bleResponse = responseConfigureSpeedMeasurementParameters(data)
                    }
                    /**配置勿扰模式*/
                    BluetoothConstant.CMD.CONFIGURE_DO_NOT_DISTURB_MODE -> {
                        bleResponse = responseConfigureDoNotDisturbMode(data)
                    }
                    /**自动设防配置*/
                    BluetoothConstant.CMD.AUTOMATIC_DEFENSE_CONFIGURATION -> {
                        bleResponse = responseAutomaticDefenseConfiguration(data)
                    }
                    /**查询自动落锁配置*/
                    BluetoothConstant.CMD.QUERY_AUTOMATIC_LOCK_CONFIGURATION -> {
                        bleResponse = responseQueryAutoMaticLockConfiguration(data)
                    }
                    /**查询测速参数*/
                    BluetoothConstant.CMD.QUERY_SPEED_MEASUREMENT_PARAMETERS -> {
                        bleResponse = responseQuerySpeedMeasurementParameters(data)
                    }
                    /**查询设备CCID*/
                    BluetoothConstant.CMD.QUERY_CCID -> {
                        bleResponse = responseQueryCCID(data)
                    }
                    /**配置APN*/
                    BluetoothConstant.CMD.CONFIGURE_APN -> {
                        bleResponse = responseConfigureAPN(data)
                    }
                    /**查询网络状态*/
                    BluetoothConstant.CMD.QUERY_NETWORK_STATUS -> {
                        bleResponse = responseQueryNetworkStatus(data)
                    }
                    /**进入存储模式*/
                    BluetoothConstant.CMD.ENTER_STORAGE_MODE -> {
                        bleResponse = responseEnterStorageMode(data)
                    }
                    /**查询状态信息_V3*/
                    BluetoothConstant.CMD.QUERY_STATUS_INFORMATION_V3 -> {
                        bleResponse = responseQueryStatusInformationV3(data)
                    }
                    /**查询上次道钉信息*/
                    BluetoothConstant.CMD.QUERY_LAST_SPIKE_INFORMATION -> {
                        bleResponse = responseQueryLastSpikeInformation(data)
                    }
                    /**头盔锁解锁*/
                    BluetoothConstant.CMD.HELMET_LOCK_UNLOCK -> {
                        bleResponse = responseHelmetLockUnlock(data)
                    }
                    /**有道钉设防*/
                    BluetoothConstant.CMD.FORTIFICATION_WITH_SPIKES -> {
                        bleResponse = responseFortificationWithSpikes(data)
                    }
                    /**有道钉启动*/
                    BluetoothConstant.CMD.START_WITH_SPIKES -> {
                        bleResponse = responseStartWithSpikes(data)
                    }
                    /**打开电池锁*/
                    BluetoothConstant.CMD.OPEN_BATTERY_LOCK -> {
                        bleResponse = responseOpenBatteryLock(data)
                    }
                    /**启动车辆(指定类型)*/
                    BluetoothConstant.CMD.START_VEHICLE_SPECIFIED_TYPE -> {
                        bleResponse = responseStartVehicleSpecifiedType(data)
                    }
                    /**设防车辆(指定类型)*/
                    BluetoothConstant.CMD.FORTIFIED_VEHICLES_SPECIFIED_TYPES -> {
                        bleResponse = responseFortifiedVehiclesSpecifiedTypes(data)
                    }
                    /**头盔锁控制（带参数）*/
                    BluetoothConstant.CMD.HELMET_LOCK_CONTROL_WITH_PARAMETERS -> {
                        bleResponse = responseHelmetLockControlWithParameters(data)
                    }
                }
                bleResponse!!.cmd = cmd
            }

            return bleResponse
        }

        /**重启*/
        fun getByteReBoot(): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.REBOOT,
                BluetoothConstant.getTokenBytes(),
                4,
            )
        }

        fun responseReboot(data: ByteArray): BleResponse {
            LogUtil.e("responseReBoot", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**播放语音*/
        fun getBytePlayVoice(blePlayVoiceModel: BlePlayVoiceModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.PLAY_VOICE,
                blePlayVoiceModel.packData(),
                blePlayVoiceModel.getLen()
            )
        }

        fun responsePlayVoice(data: ByteArray): BleResponse {
            LogUtil.e("responseReBoot", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**查询状态信息*/
        fun getByteQueryStatusInformation(): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.QUERY_STATUS_INFORMATION,
                BluetoothConstant.getTokenBytes(),
                4
            )
        }

        fun responseQueryStatusInformation(data: ByteArray): BleResponse {
            val res = BleQueryStatusInformationResponse(data)
            LogUtil.e("responseQueryStatusInformation", res.toString())
            return BleResponse(null, res)
        }

        /**设防*/
        fun getByteFortify(bleFortifyModel: BleFortifyModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.FORTIFY,
                bleFortifyModel.packData(),
                bleFortifyModel.getLen()
            )
        }

        fun responseFortify(data: ByteArray): BleResponse {
            LogUtil.e("responseFortify", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**启动*/
        fun getByteStartUp(model: BleStartUpModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.START_UP,
                model.packData(),
                model.getLen()
            )
        }

        fun responseStartUp(data: ByteArray): BleResponse {
            LogUtil.e("responseStartUp", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**熄火*/
        fun getByteFlameOut(model: BleFlameOutModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.FLAME_OUT,
                model.packData(),
                model.getLen()
            )
        }

        fun responseFlameOut(data: ByteArray): BleResponse {
            LogUtil.e("responseFlameOut", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**后轮锁解锁*/
        fun getByteUnlockRearWheel(model: BleUnlockRearWheelModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.UNLOCK_REAR_WHEEL,
                model.packData(),
                model.getLen()
            )
        }

        fun responseUnlockRearWheel(data: ByteArray): BleResponse {
            LogUtil.e("responseUnlockRearWheel", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**后轮锁上锁*/
        fun getByteLockRearWheel(model: BleLockRearWheelModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.LOCK_REAR_WHEEL,
                model.packData(),
                model.getLen()
            )
        }

        fun responseLockRearWheel(data: ByteArray): BleResponse {
            LogUtil.e("responseLockRearWheel", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**关机*/
        fun getByteShutDown(): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.SHUT_DOWN,
                BluetoothConstant.getTokenBytes(),
                4
            )
        }

        fun responseShutDown(data: ByteArray): BleResponse {
            LogUtil.e("responseShutDown", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**查询GPS信息*/
        fun getByteQueryGps(): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.QUERY_GPS,
                BluetoothConstant.getTokenBytes(),
                4
            )
        }

        fun responseQueryGps(data: ByteArray): BleResponse {
            LogUtil.e("responseQueryGps", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**查询设备IMSI*/
        fun getByteQueryIMSI(): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.QUERY_IMSI,
                BluetoothConstant.getTokenBytes(),
                4
            )
        }

        fun responseQueryIMSI(data: ByteArray): BleResponse {
            val res = NumberUtil.byteArrayToString(data)
            LogUtil.e("responseQueryIMSI", "查询设备IMSI ${res}")
            return BleResponse(null, res)
        }

        /**电池仓锁解锁*/
        fun getByteUnLockBatteryCompartmentLock(): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.UNLOCK_BATTERY_COMPARTMENT_LOCK,
                BluetoothConstant.getTokenBytes(),
                4,
            )
        }

        fun responseUnLockBatteryCompartmentLock(data: ByteArray): BleResponse {
            LogUtil.e("responseUnLockBatteryCompartmentLock", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**电池仓锁上锁*/
        fun getByteLockBatteryCompartment(model: BleLockBatteryCompartmentModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.LOCK_BATTERY_COMPARTMENT,
                model.packData(),
                model.getLen()
            )
        }

        fun responseLockBatteryCompartment(data: ByteArray): BleResponse {
            LogUtil.e("responseLockBatteryCompartment", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**配置测速参数*/
        fun getByteConfigureSpeedMeasurementParameters(model: BleConfigureSpeedMeasurementParametersModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.CONFIGURE_SPEED_MEASUREMENT_PARAMETERS,
                model.packData(),
                model.getLen()
            )
        }

        fun responseConfigureSpeedMeasurementParameters(data: ByteArray): BleResponse {
            LogUtil.e("responseConfigureSpeedMeasurementParameters", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**配置勿扰模式*/
        fun getByteConfigureDoNotDisturbMode(model: BleConfigureDoNotDisturbModeModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.CONFIGURE_DO_NOT_DISTURB_MODE,
                model.packData(),
                model.getLen()
            )
        }

        fun responseConfigureDoNotDisturbMode(data: ByteArray): BleResponse {
            LogUtil.e("responseConfigureDoNotDisturbMode", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**自动设防配置*/
        fun getByteAutomaticDefenseConfiguration(model: BleAutomaticDefenseConfigurationModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.AUTOMATIC_DEFENSE_CONFIGURATION,
                model.packData(),
                model.getLen()
            )
        }

        fun responseAutomaticDefenseConfiguration(data: ByteArray): BleResponse {
            LogUtil.e("responseAutomaticDefenseConfiguration", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**查询自动落锁配置*/
        fun getByteQueryAutoMaticLockConfiguration(): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.QUERY_AUTOMATIC_LOCK_CONFIGURATION,
                BluetoothConstant.getTokenBytes(),
                4
            )
        }

        fun responseQueryAutoMaticLockConfiguration(data: ByteArray): BleResponse {
            val res = BleQueryAutoMaticLockConfigurationResponse(data)
            LogUtil.e(
                "responseQueryAutoMaticLockConfiguration", res.toString()
            )
            return BleResponse(null, res)
        }

        /**查询测速参数*/
        fun getByteQuerySpeedMeasurementParameters(): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.QUERY_SPEED_MEASUREMENT_PARAMETERS,
                BluetoothConstant.getTokenBytes(),
                4
            )
        }

        fun responseQuerySpeedMeasurementParameters(data: ByteArray): BleResponse {
            val res = BleQuerySpeedMeasurementParametersResponse(data)
            LogUtil.e("responseQuerySpeedMeasurementParameters", res.toString())
            return BleResponse(null, res)
        }

        /**查询设备CCID*/
        fun getByteQueryCCID(): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.QUERY_CCID,
                BluetoothConstant.getTokenBytes(),
                4
            )
        }

        fun responseQueryCCID(data: ByteArray): BleResponse {
            val res = String(data)
            LogUtil.e("responseQueryCCID", res)
            return BleResponse(null, res)
        }

        /**配置APN*/
        fun getByteConfigureAPN(model: BleConfigureAPNModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.CONFIGURE_APN,
                model.packData(),
                model.getLen()
            )
        }

        fun responseConfigureAPN(data: ByteArray): BleResponse {
            LogUtil.e("responseConfigureAPN", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**查询网络状态*/
        fun getByteQueryNetworkStatus(): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.QUERY_NETWORK_STATUS,
                BluetoothConstant.getTokenBytes(),
                4
            )
        }

        fun responseQueryNetworkStatus(data: ByteArray): BleResponse {
            val res = BleQueryNetworkStatusResponse(data)
            LogUtil.e("responseQueryNetworkStatus", res.toString())
            return BleResponse(getError(data[0]).msg, res)
        }

        /**进入存储模式*/
        fun getByteEnterStorageMode(model: BleEnterStorageModeModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.ENTER_STORAGE_MODE,
                model.packData(),
                model.getLen()
            )
        }

        fun responseEnterStorageMode(data: ByteArray): BleResponse? {
            return null
        }

        /**查询状态信息_V3*/
        fun getByteQueryStatusInformationV3(): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.QUERY_STATUS_INFORMATION_V3,
                BluetoothConstant.getTokenBytes(),
                4
            )
        }

        fun responseQueryStatusInformationV3(data: ByteArray): BleResponse? {
            return null
        }

        /**查询上次道钉信息*/
        fun getByteQueryLastSpikeInformation(): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.QUERY_LAST_SPIKE_INFORMATION,
                BluetoothConstant.getTokenBytes(),
                4
            )
        }

        fun responseQueryLastSpikeInformation(data: ByteArray): BleResponse? {
            return null
        }

        /**头盔锁解锁*/
        fun getByteHelmetLockUnlock(model: BleHelmetLockUnlockModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.HELMET_LOCK_UNLOCK,
                model.packData(),
                model.getLen()
            )
        }

        fun responseHelmetLockUnlock(data: ByteArray): BleResponse {
            LogUtil.e("responseHelmetLockUnlock", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**有道钉设防*/
        fun getByteFortificationWithSpikes(model: BleFortificationWithSpikesModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.FORTIFICATION_WITH_SPIKES,
                model.packData(),
                model.getLen()
            )
        }

        fun responseFortificationWithSpikes(data: ByteArray): BleResponse {
            LogUtil.e("responseFortificationWithSpikes", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**有道钉启动*/
        fun getByteStartWithSpikes(model: BleStartWithSpikesModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.START_WITH_SPIKES,
                model.packData(),
                model.getLen()
            )
        }

        fun responseStartWithSpikes(data: ByteArray): BleResponse {
            LogUtil.e("responseStartWithSpikes", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**打开电池锁*/
        fun getByteOpenBatteryLock(model: BleOpenBatteryLockModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.OPEN_BATTERY_LOCK,
                model.packData(),
                model.getLen()
            )
        }

        fun responseOpenBatteryLock(data: ByteArray): BleResponse {
            LogUtil.e("responseOpenBatteryLock", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**启动车辆(指定类型)*/
        fun getByteStartVehicleSpecifiedType(model: BleStartVehicleSpecifiedTypeModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.START_VEHICLE_SPECIFIED_TYPE,
                model.packData(),
                model.getLen()
            )
        }

        fun responseStartVehicleSpecifiedType(data: ByteArray): BleResponse {
            LogUtil.e("responseStartVehicleSpecifiedType", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        /**设防车辆(指定类型)*/
        fun getByteFortifiedVehiclesSpecifiedTypes(model: BleFortifiedVehiclesSpecifiedTypesModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.FORTIFIED_VEHICLES_SPECIFIED_TYPES,
                model.packData(),
                model.getLen()
            )
        }

        fun responseFortifiedVehiclesSpecifiedTypes(data: ByteArray): BleResponse {
            val res = BleFortifiedVehiclesSpecifiedTypesResponse(data)
            LogUtil.e("responseFortifiedVehiclesSpecifiedTypes", res.toString())
            return BleResponse(getError(data[0]).msg, res)
        }

        /**头盔锁控制（带参数）*/
        fun getByteHelmetLockControlWithParameters(model: BleHelmetLockControlWithParametersModel): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.HELMET_LOCK_CONTROL_WITH_PARAMETERS,
                model.packData(),
                model.getLen()
            )
        }

        fun responseHelmetLockControlWithParameters(data: ByteArray): BleResponse {
            LogUtil.e("responseHelmetLockControlWithParameters", getError(data[0]).msg)
            return BleResponse(getError(data[0]).msg, null)
        }

        fun getError(byte: Byte): BluetoothConstant.BleError {
            return BluetoothConstant.BLE_ERROR.get(byte.toInt())
        }

    }
}