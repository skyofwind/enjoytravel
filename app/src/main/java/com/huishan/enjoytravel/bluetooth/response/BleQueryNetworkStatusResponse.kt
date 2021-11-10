package com.huishan.enjoytravel.bluetooth.response

import com.huishan.enjoytravel.bluetooth.NumberUtil

class BleQueryNetworkStatusResponse(data: ByteArray) {
    var state: UInt? = null
    var cnt: UInt? = null

    init {
        state = data[0].toUInt()
        cnt = NumberUtil.byteArrayToUIntBigEndian(data.copyOfRange(1, 5))
    }

    override fun toString(): String {
        return "state = $state, cnt = $cnt"
    }
}