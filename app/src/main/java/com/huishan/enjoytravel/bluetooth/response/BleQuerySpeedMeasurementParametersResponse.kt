package com.huishan.enjoytravel.bluetooth.response

import com.huishan.enjoytravel.bluetooth.NumberUtil

class BleQuerySpeedMeasurementParametersResponse(data: ByteArray) {
    var wheelDiameter: Float? = null
    var polePairNum: UInt? = null
    var SpeedLimit: UInt? = null
    var isOverSpeedOn: UInt? = null

    init {
        wheelDiameter = NumberUtil.byteArrayToFloatBidEndian(data.copyOfRange(0, 4))
        polePairNum = data[4].toUInt()
        SpeedLimit = data[5].toUInt()
        isOverSpeedOn = data[6].toUInt()
    }

    override fun toString(): String {
        return "wheelDiameter = $wheelDiameter, polePairNum = $polePairNum, SpeedLimit = $SpeedLimit, isOverSpeedOn = $isOverSpeedOn"
    }
}