package com.examen.hackernews.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.examen.hackernews.model.Articulo

@Dao
interface HackerNewsDAO {

    @Query("select * from articulo")
    fun getAll(): LiveData<List<Articulo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(articulos:List<Articulo>)

    @Query("delete from articulo where objectID = :id")
    fun delete(id:Long)
}