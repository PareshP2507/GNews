package org.psquare.util

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Exception(val e: Throwable) : NetworkResult<Nothing>()
}

suspend fun <T> safeApiAll(block: suspend () -> T): NetworkResult<T> = try {
    NetworkResult.Success(block.invoke())
} catch (throwable: Throwable) {
    NetworkResult.Exception(throwable)
}