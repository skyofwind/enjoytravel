package com.huishan.enjoytravel.bluetooth.response

import com.huishan.enjoytravel.bluetooth.NumberUtil

class BleQueryAutoMaticLockConfigurationResponse(byteArray: ByteArray) {
    var isAutolockOn: UInt? = null
    var period: UInt? = null

    init {
        isAutolockOn = byteArray[0].toUInt()
        period = NumberUtil.byteArrayToUIntBigEndian(byteArray.copyOfRange(1, 3))
    }

    override fun toString(): String {
        return "isAutolockOn = ${isAutolockOn}, period = ${period}"
    }
}