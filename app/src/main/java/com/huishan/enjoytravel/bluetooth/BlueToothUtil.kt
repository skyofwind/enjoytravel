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
                    /**??????*/
                    BluetoothConstant.CMD.REBOOT -> {
                        bleResponse = responseReboot(data)
                    }
                    /**????????????*/
                    BluetoothConstant.CMD.PLAY_VOICE -> {
                        bleResponse = responsePlayVoice(data)
                    }
                    /**??????????????????*/
                    BluetoothConstant.CMD.QUERY_STATUS_INFORMATION -> {
                        bleResponse = responseQueryStatusInformation(data)
                    }
                    /**??????*/
                    BluetoothConstant.CMD.FORTIFY -> {
                        bleResponse = responseFortify(data)
                    }
                    /**??????*/
                    BluetoothConstant.CMD.START_UP -> {
                        bleResponse = responseStartUp(data)
                    }
                    /**??????*/
                    BluetoothConstant.CMD.FLAME_OUT -> {
                        bleResponse = responseFlameOut(data)
                    }
                    /**???????????????*/
                    BluetoothConstant.CMD.UNLOCK_REAR_WHEEL -> {
                        bleResponse = responseUnlockRearWheel(data)
                    }
                    /**???????????????*/
                    BluetoothConstant.CMD.LOCK_REAR_WHEEL -> {
                        bleResponse = responseLockRearWheel(data)
                    }
                    /**??????*/
                    BluetoothConstant.CMD.SHUT_DOWN -> {
                        bleResponse = responseShutDown(data)
                    }
                    /**??????GPS??????*/
                    BluetoothConstant.CMD.QUERY_GPS -> {
                        bleResponse = responseQueryGps(data)
                    }
                    /**????????????IMSI*/
                    BluetoothConstant.CMD.QUERY_IMSI -> {
                        bleResponse = responseQueryIMSI(data)
                    }
                    /**??????????????????*/
                    BluetoothConstant.CMD.UNLOCK_BATTERY_COMPARTMENT_LOCK -> {
                        bleResponse = responseUnLockBatteryCompartmentLock(data)
                    }
                    /**??????????????????*/
                    BluetoothConstant.CMD.LOCK_BATTERY_COMPARTMENT -> {
                        bleResponse = responseLockBatteryCompartment(data)
                    }
                    /**??????????????????*/
                    BluetoothConstant.CMD.CONFIGURE_SPEED_MEASUREMENT_PARAMETERS -> {
                        bleResponse = responseConfigureSpeedMeasurementParameters(data)
                    }
                    /**??????????????????*/
                    BluetoothConstant.CMD.CONFIGURE_DO_NOT_DISTURB_MODE -> {
                        bleResponse = responseConfigureDoNotDisturbMode(data)
                    }
                    /**??????????????????*/
                    BluetoothConstant.CMD.AUTOMATIC_DEFENSE_CONFIGURATION -> {
                        bleResponse = responseAutomaticDefenseConfiguration(data)
                    }
                    /**????????????????????????*/
                    BluetoothConstant.CMD.QUERY_AUTOMATIC_LOCK_CONFIGURATION -> {
                        bleResponse = responseQueryAutoMaticLockConfiguration(data)
                    }
                    /**??????????????????*/
                    BluetoothConstant.CMD.QUERY_SPEED_MEASUREMENT_PARAMETERS -> {
                        bleResponse = responseQuerySpeedMeasurementParameters(data)
                    }
                    /**????????????CCID*/
                    BluetoothConstant.CMD.QUERY_CCID -> {
                        bleResponse = responseQueryCCID(data)
                    }
                    /**??????APN*/
                    BluetoothConstant.CMD.CONFIGURE_APN -> {
                        bleResponse = responseConfigureAPN(data)
                    }
                    /**??????????????????*/
                    BluetoothConstant.CMD.QUERY_NETWORK_STATUS -> {
                        bleResponse = responseQueryNetworkStatus(data)
                    }
                    /**??????????????????*/
                    BluetoothConstant.CMD.ENTER_STORAGE_MODE -> {
                        bleResponse = responseEnterStorageMode(data)
                    }
                    /**??????????????????_V3*/
                    BluetoothConstant.CMD.QUERY_STATUS_INFORMATION_V3 -> {
                        bleResponse = responseQueryStatusInformationV3(data)
                    }
                    /**????????????????????????*/
                    BluetoothConstant.CMD.QUERY_LAST_SPIKE_INFORMATION -> {
                        bleResponse = responseQueryLastSpikeInformation(data)
                    }
                    /**???????????????*/
                    BluetoothConstant.CMD.HELMET_LOCK_UNLOCK -> {
                        bleResponse = responseHelmetLockUnlock(data)
                    }
                    /**???????????????*/
                    BluetoothConstant.CMD.FORTIFICATION_WITH_SPIKES -> {
                        bleResponse = responseFortificationWithSpikes(data)
                    }
                    /**???????????????*/
                    BluetoothConstant.CMD.START_WITH_SPIKES -> {
                        bleResponse = responseStartWithSpikes(data)
                    }
                    /**???????????????*/
                    BluetoothConstant.CMD.OPEN_BATTERY_LOCK -> {
                        bleResponse = responseOpenBatteryLock(data)
                    }
                    /**????????????(????????????)*/
                    BluetoothConstant.CMD.START_VEHICLE_SPECIFIED_TYPE -> {
                        bleResponse = responseStartVehicleSpecifiedType(data)
                    }
                    /**????????????(????????????)*/
                    BluetoothConstant.CMD.FORTIFIED_VEHICLES_SPECIFIED_TYPES -> {
                        bleResponse = responseFortifiedVehiclesSpecifiedTypes(data)
                    }
                    /**??????????????????????????????*/
                    BluetoothConstant.CMD.HELMET_LOCK_CONTROL_WITH_PARAMETERS -> {
                        bleResponse = responseHelmetLockControlWithParameters(data)
                    }
                }
                bleResponse!!.cmd = cmd
            }

            return bleResponse
        }

        /**??????*/
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

        /**????????????*/
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

        /**??????????????????*/
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

        /**??????*/
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

        /**??????*/
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

        /**??????*/
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

        /**???????????????*/
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

        /**???????????????*/
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

        /**??????*/
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

        /**??????GPS??????*/
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

        /**????????????IMSI*/
        fun getByteQueryIMSI(): ByteArray {
            return NumberUtil.packData(
                BluetoothConstant.CMD.QUERY_IMSI,
                BluetoothConstant.getTokenBytes(),
                4
            )
        }

        fun responseQueryIMSI(data: ByteArray): BleResponse {
            val res = NumberUtil.byteArrayToString(data)
            LogUtil.e("responseQueryIMSI", "????????????IMSI ${res}")
            return BleResponse(null, res)
        }

        /**??????????????????*/
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

        /**??????????????????*/
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

        /**??????????????????*/
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

        /**??????????????????*/
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

        /**??????????????????*/
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

        /**????????????????????????*/
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

        /**??????????????????*/
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

        /**????????????CCID*/
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

        /**??????APN*/
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

        /**??????????????????*/
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

        /**??????????????????*/
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

        /**??????????????????_V3*/
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

        /**????????????????????????*/
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

        /**???????????????*/
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

        /**???????????????*/
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

        /**???????????????*/
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

        /**???????????????*/
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

        /**????????????(????????????)*/
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

        /**????????????(????????????)*/
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

        /**??????????????????????????????*/
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