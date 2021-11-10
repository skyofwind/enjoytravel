package com.huishan.enjoytravel.data.remote.request

/**
 * 获取换电阔值
 * @param serviceAreaId 服务区Id
 */
data class GetChangeBatteryRequest(
    var serviceAreaId: Int?
)