package com.huishan.enjoytravel.bluetooth.model

import com.huishan.enjoytravel.bluetooth.BluetoothConstant
import com.huishan.enjoytravel.bluetooth.NumberUtil

class BleFortifiedVehiclesSpecifiedTypesModel(val type: Int, val reserved: Long, val idx: Int, val volume: Int): BaseBleModel() {
    override fun packData(): ByteArray {
        bytes = NumberUtil.getSumByteArray(
            BluetoothConstant.getTokenBytes(),
            NumberUtil.intToByteArrayBigEndian(type, 1),
            NumberUtil.longToByteArrayBigEndian(reserved, 4),
            NumberUtil.intToByteArrayBigEndian(idx, 1),
            NumberUtil.intToByteArrayBigEndian(volume, 1)
        )

        return bytes!!
    }

}