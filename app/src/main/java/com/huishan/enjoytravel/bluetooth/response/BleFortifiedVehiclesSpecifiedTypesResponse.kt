package com.huishan.enjoytravel.bluetooth.response

class BleFortifiedVehiclesSpecifiedTypesResponse(data: ByteArray) {
    var errCode: UInt? = null
    var type: UInt? = null
    var payload: ByteArray? = null

    init {
        errCode = data[0].toUInt()
        type = data[1].toUInt()
        payload = data.copyOfRange(2, data.size - 2)
    }

    override fun toString(): String {
        return "errCode = $errCode, type = $type, payload = ${payload.toString()}"
    }
}