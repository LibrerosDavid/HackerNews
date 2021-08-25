package com.examen.hackernews.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.examen.hackernews.model.Articulo

@Database(
    entities = arrayOf(Articulo::class),
    version = 1
)
@TypeConverters(DataTypeConverter::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun hackerNewsDAO():HackerNewsDAO
}