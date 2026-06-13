package com.example.newspulseapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulseapp.data.model.Article
import com.example.newspulseapp.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val repository = NewsRepository()

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    val savedArticles = mutableStateListOf<Article>()

    init {
        loadTopHeadlines()
    }

    fun loadTopHeadlines() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading

            try {
                val response = repository.getTopHeadlines()
                val articles = response.articles.orEmpty()
                    .filter { !it.title.isNullOrBlank() }

                _uiState.value = NewsUiState.Success(articles)
            } catch (e: Exception) {
                _uiState.value = NewsUiState.Error(
                    e.message ?: "Gagal memuat berita"
                )
            }
        }
    }

    fun searchNews(query: String) {
        if (query.isBlank()) {
            loadTopHeadlines()
            return
        }

        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading

            try {
                val response = repository.searchNews(query)
                val articles = response.articles.orEmpty()
                    .filter { !it.title.isNullOrBlank() }

                _uiState.value = NewsUiState.Success(articles)
            } catch (e: Exception) {
                _uiState.value = NewsUiState.Error(
                    e.message ?: "Gagal mencari berita"
                )
            }
        }
    }

    fun toggleSavedArticle(article: Article) {
        if (isArticleSaved(article)) {
            savedArticles.removeAll { it.url == article.url }
        } else {
            savedArticles.add(article)
        }
    }

    fun isArticleSaved(article: Article): Boolean {
        return savedArticles.any { it.url == article.url }
    }
}