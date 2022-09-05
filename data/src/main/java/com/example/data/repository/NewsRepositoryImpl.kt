package com.example.data.repository

import com.example.data.datasource.NewsLocalDataSource
import com.example.data.datasource.NewsRemoteDataSource
import com.example.data.network.model.toModel
import com.example.domain.model.News
import com.example.domain.repository.NewsRepository
import com.example.domain.utils.Result
import com.example.domain.utils.doOnSuccess
import com.example.domain.utils.mapSuccess

class NewsRepositoryImpl(
    private val newsLocalDataSource: NewsLocalDataSource,
    private val newsRemoteDataSource: NewsRemoteDataSource
): NewsRepository {

    override suspend fun getNews(): Result<List<News>, Throwable> {
        return newsRemoteDataSource.getNews()
            .doOnSuccess { newsLocalDataSource.setNews(it) }
            .mapSuccess { it.map { news -> news.toModel() } }
    }

    override suspend fun getLocalNews(): List<News>? =
        newsLocalDataSource.getNews()?.map { it.toModel() }

}