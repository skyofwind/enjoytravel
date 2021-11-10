package com.huishan.enjoytravel.data.remote.entity

data class ItemMoveCarEntity(
    val bikeNumber: String = "1006000666",
    val bikeStatus: String = "待租",
    val currentPower: String = "80%",
    val action: String = "操作",
    var checked: Boolean = false
) {
}