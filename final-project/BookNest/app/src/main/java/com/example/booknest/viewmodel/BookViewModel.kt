package com.example.booknest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booknest.data.local.entity.Book
import com.example.booknest.data.local.entity.BorrowHistory
import com.example.booknest.data.repository.BookRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BookViewModel(
    private val repository: BookRepository
) : ViewModel() {

    val books: StateFlow<List<Book>> = repository.allBooks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val histories: StateFlow<List<BorrowHistory>> = repository.allHistories
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addBook(
        title: String,
        author: String,
        category: String,
        year: String,
        description: String,
        coverUri: String?
    ) {
        viewModelScope.launch {
            repository.insertBook(
                Book(
                    title = title,
                    author = author,
                    category = category,
                    year = year,
                    description = description,
                    coverUri = coverUri,
                    coverColor = generateCoverColor(title)
                )
            )
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }

    fun borrowBook(
        book: Book,
        borrowerName: String
    ) {
        viewModelScope.launch {
            repository.borrowBook(
                book = book,
                borrowerName = borrowerName,
                borrowDate = getCurrentDate()
            )
        }
    }

    fun returnBook(book: Book) {
        viewModelScope.launch {
            repository.returnBook(
                book = book,
                returnDate = getCurrentDate()
            )
        }
    }

    private fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
        return formatter.format(Date())
    }

    private fun generateCoverColor(title: String): Long {
        val colors = listOf(
            0xFF2563EB,
            0xFF7C3AED,
            0xFFDB2777,
            0xFFEA580C,
            0xFF16A34A,
            0xFF0891B2,
            0xFF9333EA
        )

        val index = if (title.isBlank()) {
            0
        } else {
            kotlin.math.abs(title.hashCode()) % colors.size
        }

        return colors[index]
    }
}