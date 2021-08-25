package com.examen.hackernews.util

import java.lang.Exception

sealed class Resource<out T> {

    class Loading<out T>:Resource<T>()
    class Success<out T>(val data:T):Resource<T>()
    class Fail<out T>(exception: Exception, val data:T):Resource<T>()
}