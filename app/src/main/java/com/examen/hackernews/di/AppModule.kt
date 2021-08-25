package com.examen.hackernews.di

import android.content.Context
import androidx.room.Room
import com.examen.hackernews.api.HackerNewsService
import com.examen.hackernews.db.AppDataBase
import com.examen.hackernews.db.HackerNewsDAO
import com.examen.hackernews.util.LiveDataCallAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.threeten.bp.LocalDateTime
import java.util.concurrent.TimeUnit


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerOkHttp(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addNetworkInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providerGson(): Gson {
        val gson = GsonBuilder().registerTypeAdapter(
            LocalDateTime::class.java,
            JsonDeserializer<LocalDateTime?> { json, type, jsonDeserializationContext ->
                if (json.asJsonPrimitive.asString.isNotEmpty()){
                    LocalDateTime.parse(json.asJsonPrimitive.asString.substring(0,json.asJsonPrimitive.asString.length-1))
                }else{
                    null
                }
            }
        ).create()
        return gson
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient,gson: Gson): HackerNewsService = Retrofit.Builder()
        .baseUrl("https://hn.algolia.com/api/v1/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build().create(HackerNewsService::class.java)

    // con esto marco que solo exista una
    @Provides
    @Singleton
    fun provideDB(
        @ApplicationContext context: Context
    ): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "ApiData")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideHackerNewsDao(db: AppDataBase): HackerNewsDAO{
        return db.hackerNewsDAO()
    }
}