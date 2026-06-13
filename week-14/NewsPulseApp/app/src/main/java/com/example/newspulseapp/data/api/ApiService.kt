package com.example.newspulseapp.data.api

import com.example.newspulseapp.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("category") category: String = "technology",
        @Query("apiKey") apiKey: String
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("language") language: String = "en",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String
    ): NewsResponse
}