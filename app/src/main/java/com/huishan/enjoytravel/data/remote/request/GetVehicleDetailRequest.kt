package com.huishan.enjoytravel.data.remote.request

/**
 * 获得车辆详情
 * @param lat 纬度高德
 * @param lng 经度高德
 * @param vehicleCode 车辆编号
 */
data class GetVehicleDetailRequest(
    var lat: String,
    var lng: String,
    var vehicleCode: String
)