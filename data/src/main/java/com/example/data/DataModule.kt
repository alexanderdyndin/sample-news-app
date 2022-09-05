package com.example.data

import com.example.data.datasource.NewsLocalDataSource
import com.example.data.datasource.NewsRemoteDataSource
import com.example.data.db.PreferencesDS
import com.example.data.db.PreferencesLocalDS
import com.example.data.db.utils.PathProvider
import com.example.data.db.utils.buildDB
import com.example.data.network.NewsApiService
import com.example.data.network.retrofitUtils.buildRetrofitBuilder
import com.example.data.repository.NewsRepositoryImpl
import com.example.domain.repository.NewsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val dataModule = module {
    single { PathProvider(get()) }
    single { buildDB(get()) }
    single<PreferencesDS> { PreferencesLocalDS(get()) }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single { OkHttpClient.Builder() }
    single {
        get<OkHttpClient.Builder>()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    single {
        buildRetrofitBuilder(get(), "http://jsonplaceholder.typicode.com/") //fake api
            .build()
            .create(NewsApiService::class.java)
    }

    single { NewsLocalDataSource(get()) }
    single { NewsRemoteDataSource(get()) }
    single<NewsRepository> { NewsRepositoryImpl(get(), get()) }
}