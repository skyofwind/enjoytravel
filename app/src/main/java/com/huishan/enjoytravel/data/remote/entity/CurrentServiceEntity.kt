package com.huishan.enjoytravel.data.remote.entity

data class CurrentServiceEntity(
    val address: String?,
    val checkFlag: Boolean?,
    val code: String?,
    val createBy: String?,
    val createTime: String?,
    val delFalg: Int?,
    val deptId: Int?,
    val id: Int?,
    val lat: Int?,
    val lng: Int?,
    val m2Area: Int?,
    val name: String?,
    val opUserId: Int?,
    val params: Any?,
    val qeuryDeptIds: IntArray?,
    val regionArray: String?,
    val remark: String?,
    val searchValue: String?,
    val serviceAreaId: Int?,
    val status: Int?,
    val type: Int?,
    val updateBy: String?,
    val updateTime: String?,
    val vehicleNum: Int?
)