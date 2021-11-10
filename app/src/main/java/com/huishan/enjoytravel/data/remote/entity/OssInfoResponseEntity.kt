package com.huishan.enjoytravel.data.remote.entity

data class OssInfoResponseEntity(
    val accessKeyId: String?,
    val accessKeySecret: String?,
    val bucketName: String?,
    val bucketUrl: String?,
    val endpoint: String?,
    val expiration: String?,
    val securityToken: String?
)