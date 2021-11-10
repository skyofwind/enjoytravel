package com.huishan.enjoytravel.bluetooth.model

import com.huishan.enjoytravel.bluetooth.BluetoothConstant
import com.huishan.enjoytravel.bluetooth.NumberUtil

class BleConfigureDoNotDisturbModeModel(val mode: Int): BaseBleModel() {
    override fun packData(): ByteArray {
        bytes = NumberUtil.getSumByteArray(
            BluetoothConstant.getTokenBytes(),
            NumberUtil.intToByteArrayBigEndian(mode, 1)
        )

        return bytes!!
    }
}