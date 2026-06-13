package com.example.newspulseapp.viewmodel

import com.example.newspulseapp.data.model.Article

sealed class NewsUiState {
    object Loading : NewsUiState()

    data class Success(
        val articles: List<Article>
    ) : NewsUiState()

    data class Error(
        val message: String
    ) : NewsUiState()
}