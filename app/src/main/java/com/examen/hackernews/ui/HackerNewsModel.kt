package com.examen.hackernews.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.examen.hackernews.modelData.Page
import com.examen.hackernews.repository.HackerNewsRepository
import com.examen.hackernews.util.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class HackerNewsModel @ViewModelInject constructor(
    private val hackerNewsRepository: HackerNewsRepository
):ViewModel() {
    private val page = MutableLiveData<Page>()

    val getArticulos = Transformations.switchMap(page){
        hackerNewsRepository.getArticulos(it)
    }

    fun setPage(page:Page){
        this.page.value = page
    }

}