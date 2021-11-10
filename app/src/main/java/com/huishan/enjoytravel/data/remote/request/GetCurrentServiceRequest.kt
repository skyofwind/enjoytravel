package com.huishan.enjoytravel.data.remote.request

/**
 * 查询车辆列表
 * @param lat 纬度高德
 * @param lng 经度高德
 */
data class GetCurrentServiceRequest(
    var lat: Double,
    var lng: Double
)
