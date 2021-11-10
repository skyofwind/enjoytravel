package com.huishan.enjoytravel.data.remote.request

/**
 * 查询车辆列表
 * @param serviceAreaId 服务区Id
 * @param dl 电量大于
 * @param isAsc 排序方式(desc[降序] asc[升序默认])
 * @param juli 查询距离范围内的车辆(米),经纬度不为空时有效
 * @param lat 当前纬度
 * @param lng 当前经度
 * @param orderByColumn 排序列（dl[电量] juli[距离]）
 * @param pageNum 查询页数索引
 * @param pageSize 每页显示记录数
 * @param queryStatus 查询状态 150运营车辆 100可租用 101骑行 102临时停车 105被预约 250巡检车辆 201报修 202拖回 203低电量 204换电中 205站点外 206被占用 207调度中 208已下架 350运营警示 301电瓶移除 302移动异常 303出服务区 304离线 305有单无程 306一日无单 307报失 308订单超长 309禁停区）
 * @param runTime 超长订单>=
 * @param unRunTime 超无订单>=
 * @param vehicleCodeEnd 号段<=
 * @param vehicleCodeStart 号段>=
 */
data class GetVehicleListRequest(
    var serviceAreaId: Int,
    var dl: Int?,
    var isAsc: String?,
    var juli: Int?,
    var lat: Double?,
    var lng: Double?,
    var orderByColumn: String?,
    var pageNum: Int?,
    var pageSize: Int?,
    var queryStatus: String?,
    var runTime: Int?,
    var unRunTime: Int?,
    var vehicleCodeEnd: String?,
    var vehicleCodeStart: String?
)