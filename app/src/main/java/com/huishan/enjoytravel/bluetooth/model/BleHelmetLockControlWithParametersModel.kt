package com.huishan.enjoytravel.bluetooth.model

import com.huishan.enjoytravel.bluetooth.BluetoothConstant
import com.huishan.enjoytravel.bluetooth.NumberUtil

class BleHelmetLockControlWithParametersModel(
    val sw: Int,
    val idx: Int,
    val volume: Int,
    val isAutoLock: Int,
    val isAutoOpen: Int
) : BaseBleModel() {
    override fun packData(): ByteArray {
        bytes = NumberUtil.getSumByteArray(
            BluetoothConstant.getTokenBytes(),
            NumberUtil.intToByteArrayBigEndian(sw, 1),
            NumberUtil.intToByteArrayBigEndian(idx, 1),
            NumberUtil.intToByteArrayBigEndian(volume, 1),
            NumberUtil.intToByteArrayBigEndian(isAutoLock, 1),
            NumberUtil.intToByteArrayBigEndian(isAutoOpen, 1)
        )

        return bytes!!
    }
}