package com.mad43.recipestask.domain.resources

sealed interface ResourceResult<out T> {
    data class Success<T>(val data: T?) : ResourceResult<T>
    data class Error(val error: Throwable?) : ResourceResult<Nothing>
}