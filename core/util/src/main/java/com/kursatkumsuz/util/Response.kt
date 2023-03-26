package com.kursatkumsuz.util


sealed class Response<out T> {

    data class Success<out T>(
        val data: T
    ): Response<T>()

    data class Error(
        val msg: String
    ): Response<Nothing>()

    object Loading: Response<Nothing>()

}