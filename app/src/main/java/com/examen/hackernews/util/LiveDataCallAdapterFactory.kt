package com.examen.hackernews.util

import androidx.lifecycle.LiveData
import com.examen.hackernews.api.ApiResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.IllegalArgumentException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory: CallAdapter.Factory()  {
    override fun get(returnType: Type,
                     annotations: Array<Annotation>,
                     retrofit: Retrofit
    ):
            CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType =
            getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != ApiResponse::class.java) {
            throw IllegalArgumentException("debe de ser un tipo de recurso")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("el recurso debe estar parametrizado")
        }
        val bodyType = getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(bodyType)
    }
}