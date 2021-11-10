package com.huishan.enjoytravel.data.remote.request

/**
 * 关闭电池仓
 * @param vehicleCode 车辆编号
 */
data class CloseBatteryRequest(
    var vehicleCode: String?
)