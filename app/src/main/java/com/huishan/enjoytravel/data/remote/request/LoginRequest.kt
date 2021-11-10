package com.huishan.enjoytravel.data.remote.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("grantType") var grantType: String,
    @SerializedName("account") var account: String,
    @SerializedName("password") var password: String
)