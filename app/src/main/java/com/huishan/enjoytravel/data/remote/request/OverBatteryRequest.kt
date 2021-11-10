package com.huishan.enjoytravel.data.remote.request

/**
 * 完成换电
 * @param vehicleCode 车辆编号
 */
data class OverBatteryRequest(
    var vehicleCode: String?
)