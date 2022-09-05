package com.example.data.network.model

import com.example.domain.model.News
import kotlinx.serialization.Serializable

@Serializable
data class NewsDto(
    val id: Int,
    val title: String,
    val body: String
)

fun NewsDto.toModel() = News(id = id, name = title, description = body)
