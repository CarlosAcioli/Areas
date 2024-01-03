package com.acioli.areas.utils

sealed class Results<T> (val data: T? = null, val message: String? = null) {

    class Loading<T>(val isLoading: Boolean = true, private val loadingData: T? = null): Results<T>(loadingData)
    class Success<T>(data: T?, message: String? = null): Results<T>(data, message)
    class Error<T>(message: String, data: T? = null): Results<T>(data, message)

}
