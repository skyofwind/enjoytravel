package com.huishan.enjoytravel.bluetooth.model

import com.huishan.enjoytravel.bluetooth.BluetoothConstant
import com.huishan.enjoytravel.bluetooth.NumberUtil

class BleLockRearWheelModel(val idx: Int, val volume: Int): BaseBleModel() {
    override fun packData(): ByteArray {
        bytes = NumberUtil.getSumByteArray(
            BluetoothConstant.getTokenBytes(),
            NumberUtil.intToByteArrayBigEndian(idx, 1),
            NumberUtil.intToByteArrayBigEndian(volume, 1)
        )

        return bytes!!
    }
}