package com.example.domain.repository

import com.example.domain.model.News
import com.example.domain.utils.Result

interface NewsRepository {

    suspend fun getNews(): Result<List<News>, Throwable>
    suspend fun getLocalNews(): List<News>?

}