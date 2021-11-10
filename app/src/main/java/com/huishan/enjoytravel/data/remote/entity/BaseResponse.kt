package com.huishan.enjoytravel.data.remote.entity

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("code")
    val code: Int = -1,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val data: T?
)