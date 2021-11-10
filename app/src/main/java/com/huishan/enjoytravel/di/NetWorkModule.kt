package com.huishan.enjoytravel.di

import com.huishan.enjoytravel.data.remote.BikeService
import com.huishan.enjoytravel.data.remote.HttpConstants
import com.huishan.enjoytravel.data.remote.MyLoggerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(HttpConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(HttpConstants.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(HttpConstants.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(
                MyLoggerInterceptor(
                    "",
                    HttpConstants.IS_LOG
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(HttpConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideBikeService(retrofit: Retrofit): BikeService {
        return retrofit.create(BikeService::class.java)
    }
}