package com.huishan.enjoytravel.bluetooth.model

import com.huishan.enjoytravel.bluetooth.BluetoothConstant
import com.huishan.enjoytravel.bluetooth.NumberUtil

class BleConfigureSpeedMeasurementParametersModel(
    val wheelDiameter: Float,
    val polePairNum: Int,
    val SpeedLimit: Int,
    val isOverSpeedOn: Int
) : BaseBleModel() {
    override fun packData(): ByteArray {
        bytes = NumberUtil.getSumByteArray(
            BluetoothConstant.getTokenBytes(),
            NumberUtil.floatToByteArrayBidEndian(wheelDiameter),
            NumberUtil.intToByteArrayBigEndian(polePairNum, 1),
            NumberUtil.intToByteArrayBigEndian(SpeedLimit, 1),
            NumberUtil.intToByteArrayBigEndian(isOverSpeedOn, 1)
        )

        return bytes!!
    }
}