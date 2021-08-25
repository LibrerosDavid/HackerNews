package com.examen.hackernews.repository

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import com.examen.hackernews.api.ApiResponse
import com.examen.hackernews.api.HackerNewsService
import com.examen.hackernews.db.HackerNewsDAO
import com.examen.hackernews.db.NetworkBoundResource
import com.examen.hackernews.model.Articulo
import com.examen.hackernews.model.ResponseData
import com.examen.hackernews.modelData.Page
import com.examen.hackernews.util.RateLimiter
import com.examen.hackernews.util.Resource
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HackerNewsRepository @Inject constructor(
    private val hackerNewsDAO: HackerNewsDAO,
    private val hackerNewsService: HackerNewsService
) {

    private val repoListRateLimiter = RateLimiter(10, TimeUnit.MINUTES)

    fun getArticulos(page: Page): LiveData<Resource<List<Articulo>>> {
        return object : NetworkBoundResource<List<Articulo>, ResponseData>() {
            override fun shouldFetch(data: List<Articulo>?): Boolean {
                return data == null || repoListRateLimiter.shoulFetch() || page.recarga
            }

            override fun loadFromDb(): LiveData<List<Articulo>> {
                return hackerNewsDAO.getAll()
            }

            override fun saveCallResult(item: ResponseData) {


                if (item != null){
                    hackerNewsDAO.saveAll(item.hits!!)
                }
            }

            override fun createCall(): LiveData<ApiResponse<ResponseData>> {


                return hackerNewsService.getArticulos(
                    page.query,
                    page.page
                )
            }
        }.asLiveData()
    }
}