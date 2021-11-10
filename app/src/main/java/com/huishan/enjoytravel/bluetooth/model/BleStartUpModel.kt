package com.huishan.enjoytravel.bluetooth.model

import com.huishan.enjoytravel.bluetooth.BluetoothConstant
import com.huishan.enjoytravel.bluetooth.NumberUtil

class BleStartUpModel(val idx: Int, val volume: Int, val isIgnoreFence: Int): BaseBleModel() {
    override fun packData(): ByteArray {
        bytes = NumberUtil.getSumByteArray(
            BluetoothConstant.getTokenBytes(),
            NumberUtil.intToByteArrayBigEndian(idx, 1),
            NumberUtil.intToByteArrayBigEndian(volume, 1),
            NumberUtil.intToByteArrayBigEndian(isIgnoreFence, 1)
        )

        return bytes!!
    }
}