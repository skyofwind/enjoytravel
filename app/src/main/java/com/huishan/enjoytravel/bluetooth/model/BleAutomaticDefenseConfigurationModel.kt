package com.huishan.enjoytravel.bluetooth.model

import com.huishan.enjoytravel.bluetooth.BluetoothConstant
import com.huishan.enjoytravel.bluetooth.NumberUtil

class BleAutomaticDefenseConfigurationModel(val isAutolockOn: Int, val period: Int): BaseBleModel() {
    override fun packData(): ByteArray {
        bytes = NumberUtil.getSumByteArray(
            BluetoothConstant.getTokenBytes(),
            NumberUtil.intToByteArrayBigEndian(isAutolockOn, 1),
            NumberUtil.intToByteArrayBigEndian(period, 1)
        )

        return bytes!!
    }
}