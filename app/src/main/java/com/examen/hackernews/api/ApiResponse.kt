package com.examen.hackernews.api

import android.util.Log
import retrofit2.Response

sealed class ApiResponse<T> {

    companion object {

        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            Log.e("ApiErrorResponse",error.message.toString())
            return ApiErrorResponse(error = error.message ?: "Error desconocido")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {

                val body = response.body()

                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()

                } else {
                    ApiSuccessResponse(body)
                }

            } else {

                val msg = response.errorBody()?.string()
                val msgError = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(msgError ?: "Error desconocido")
            }
        }
    }
}

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val body: T
) : ApiResponse<T>()

data class ApiErrorResponse<T>(
    val error: String
) : ApiResponse<T>()
