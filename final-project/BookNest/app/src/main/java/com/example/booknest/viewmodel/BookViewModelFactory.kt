package com.example.booknest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.booknest.data.repository.BookRepository

class BookViewModelFactory(
    private val repository: BookRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}