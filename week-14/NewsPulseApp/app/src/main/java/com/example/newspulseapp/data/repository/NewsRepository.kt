package com.example.newspulseapp.data.repository

import com.example.newspulseapp.BuildConfig
import com.example.newspulseapp.data.api.RetrofitClient

class NewsRepository {
    suspend fun getTopHeadlines() =
        RetrofitClient.apiService.getTopHeadlines(
            apiKey = BuildConfig.NEWS_API_KEY
        )

    suspend fun searchNews(query: String) =
        RetrofitClient.apiService.searchNews(
            query = query,
            apiKey = BuildConfig.NEWS_API_KEY
        )
}