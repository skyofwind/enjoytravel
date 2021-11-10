package com.huishan.enjoytravel.data

import com.huishan.enjoytravel.data.remote.BikeService
import com.huishan.enjoytravel.data.repository.BikeRepositoryImpl
import com.huishan.enjoytravel.data.repository.Repository

object BikeFactory {

    fun makeBikeRepository(api: BikeService): Repository {
        return BikeRepositoryImpl(api)
    }
}