package com.examen.hackernews.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.examen.hackernews.modelData.Page
import com.examen.hackernews.repository.HackerNewsRepository

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