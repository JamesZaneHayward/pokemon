package com.james.hayward.pokemon.data.common

import com.james.hayward.pokemon.data.mappers.Mapper
import retrofit2.HttpException
import retrofit2.Response

interface ApiHandler {
    suspend fun <T : Any> handleApi(
        execute: suspend () -> Response<T>,
    ): ApiResponse<T> {
        return try {
            val response = execute()

            if (response.isSuccessful) {
                ApiResponse.Success(
                    response.code(),
                    response.body()!!,
                )
            } else {
                ApiResponse.Error(response.code(), response.errorBody()?.string())
            }
        } catch (e: HttpException) {
            ApiResponse.Error(e.code(), e.message)
        } catch (e: Throwable) {
            ApiResponse.Exception(e)
        }
    }

    suspend fun <T : Any, U : Any> handleApiWithMapper(
        mapper: Mapper<T, U>,
        execute: suspend () -> Response<T>,
    ): ApiResponse<U> {
        return try {
            val response = execute()

            if (response.isSuccessful) {
                val mappedResponse = mapper.toDomain(response.body()!!)

                ApiResponse.Success(
                    response.code(),
                    mappedResponse,
                )
            } else {
                ApiResponse.Error(response.code(), response.errorBody()?.string())
            }
        } catch (e: HttpException) {
            ApiResponse.Error(e.code(), e.message)
        } catch (e: Throwable) {
            ApiResponse.Exception(e)
        }
    }
}
