package com.examen.hackernews.api

import androidx.lifecycle.LiveData
import com.examen.hackernews.model.ResponseData
import retrofit2.http.GET
import retrofit2.http.Query

interface HackerNewsService {

    @GET("search_by_date")
    fun getArticulos(
        @Query("query") query: String,
        @Query("page") page:Int
    ): LiveData<ApiResponse<ResponseData>>
}