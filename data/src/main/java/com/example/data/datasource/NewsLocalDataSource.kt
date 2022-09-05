package com.example.data.datasource

import com.example.data.db.PreferencesDS
import com.example.data.db.getJsonLazy
import com.example.data.db.setJsonLazy
import com.example.data.network.model.NewsDto

class NewsLocalDataSource(private val preferences: PreferencesDS) {

    companion object {
        const val KEY = "news"
    }

    suspend fun getNews() = preferences.getJsonLazy<List<NewsDto>>(KEY)

    suspend fun setNews(news: List<NewsDto>) = preferences.setJsonLazy(news, KEY)

}