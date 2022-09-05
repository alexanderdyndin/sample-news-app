package com.example.data.datasource

import com.example.data.network.NewsApiService
import com.example.domain.utils.runOperationCatching
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRemoteDataSource(private val api: NewsApiService) {

    suspend fun getNews() = withContext(Dispatchers.IO) { runOperationCatching { api.getNews() } }

}