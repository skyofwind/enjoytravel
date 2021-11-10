package com.huishan.enjoytravel.data.remote.request

/**
 * 修改换电阔值
 * @param batteryValue 换电阈值
 * @param serviceAreaId 服务区id
 */
data class SaveChangeBatteryRequest(
    var batteryValue: Int?, var serviceAreaId: Int?
)