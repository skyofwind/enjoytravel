package com.huishan.enjoytravel.bluetooth.response

data class BleResponse(
    val msg: String?,
    val data: Any?
) {
    var cmd: Byte? = null
}