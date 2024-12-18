package com.james.hayward.pokemon.data.common

sealed class ApiResponse<out T : Any> {
    data class Success<out T : Any>(val code: Int, val response: T) : ApiResponse<T>()
    data class Error<out T : Any>(val code: Int, val errorMsg: String?) : ApiResponse<T>()
    data class Exception<out T : Any>(val e: Throwable) : ApiResponse<T>()
}
