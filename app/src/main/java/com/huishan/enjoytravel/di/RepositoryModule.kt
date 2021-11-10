package com.huishan.enjoytravel.di

import com.huishan.enjoytravel.data.BikeFactory
import com.huishan.enjoytravel.data.remote.BikeService
import com.huishan.enjoytravel.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideTasksRepository(
        api: BikeService
    ): Repository {
        return BikeFactory.makeBikeRepository(api)
    }
}