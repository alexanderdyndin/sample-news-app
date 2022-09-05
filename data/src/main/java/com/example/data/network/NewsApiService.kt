package com.example.data.network

import com.example.data.network.model.NewsDto
import retrofit2.http.GET

interface NewsApiService {

    @GET("posts")
    suspend fun getNews(): List<NewsDto>

}