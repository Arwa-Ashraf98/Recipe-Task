package com.mad43.recipestask.presentation.state

sealed class ScreenState<T>(val data: T? = null, val message: String? = null) {
    class SUCCESS<T>(data: T?) : ScreenState<T>(data = data)
    class ERROR<T>(message: String?) : ScreenState<T>(message = message)
    class LOADING<T> : ScreenState<T>()
}
