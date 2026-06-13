package com.example.newspulseapp.data.model

data class NewsResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Article>?
)