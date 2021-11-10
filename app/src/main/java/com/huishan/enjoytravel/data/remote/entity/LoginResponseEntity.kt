package com.huishan.enjoytravel.data.remote.entity

data class LoginResponseEntity(
    val accessToken: String?,
    val expiresIn: Int?,
    val freshToken: String?
)