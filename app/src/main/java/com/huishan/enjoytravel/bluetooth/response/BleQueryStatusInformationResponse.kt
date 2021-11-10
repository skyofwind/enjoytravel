package com.huishan.enjoytravel.bluetooth.response

import com.huishan.enjoytravel.bluetooth.NumberUtil

class BleQueryStatusInformationResponse(byteArray: ByteArray) {
    var event: UInt? = null
    var sw: ByteArray? = null
    var gsm: UInt? = null
    var voltage: UInt? = null
    var GPSversion: String? = null
    var BLEversion: String? = null

    init {
        event = byteArray[0].toUInt()
        sw = byteArray.copyOfRange(1, 3)
        gsm = byteArray[3].toUInt()
        voltage = NumberUtil.byteArrayToUIntBigEndian(byteArray.copyOfRange(4, 7))
        GPSversion = NumberUtil.byteArrayToString(byteArray.copyOfRange(7, 10))
        BLEversion = NumberUtil.byteArrayToString(byteArray.copyOfRange(10, 13))
    }

    override fun toString(): String {

        return "event = ${event}, gsm = ${gsm}, voltage = ${voltage}, GPSversion = ${GPSversion}, BLEversion = ${BLEversion},sw = ${sw.toString()}"
    }
}