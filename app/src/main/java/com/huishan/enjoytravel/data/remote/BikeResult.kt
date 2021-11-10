package com.huishan.enjoytravel.data.remote

sealed class BikeResult<out T> {
    data class Success<out T>(val value: T) : BikeResult<T>()
    data class Failure(val throwable: Throwable?) : BikeResult<Nothing>()
}

inline fun <reified T> BikeResult<T>.doSuccess(success: (T) -> Unit) {
    if (this is BikeResult.Success) {
        success(value)
    }
}

inline fun <reified T> BikeResult<T>.doFailure(failure: (Throwable?) -> Unit) {
    if (this is BikeResult.Failure) {
        failure(throwable)
    }
}