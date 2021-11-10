package com.huishan.enjoytravel.bluetooth.model

import com.huishan.enjoytravel.bluetooth.BluetoothConstant

open abstract class BaseBleModel {
    var bytes: ByteArray? = null

    abstract fun packData(): ByteArray

    fun getLen(): Byte {
        bytes?.let {
            return it.size.toByte()
        }

        return 0
    }
}