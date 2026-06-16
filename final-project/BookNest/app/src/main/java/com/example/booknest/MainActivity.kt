package com.example.booknest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booknest.data.local.database.AppDatabase
import com.example.booknest.data.repository.BookRepository
import com.example.booknest.navigation.AppNavGraph
import com.example.booknest.ui.theme.BookNestTheme
import com.example.booknest.viewmodel.BookViewModel
import com.example.booknest.viewmodel.BookViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)

        val repository = BookRepository(
            bookDao = database.bookDao(),
            borrowHistoryDao = database.borrowHistoryDao()
        )

        val factory = BookViewModelFactory(repository)

        setContent {
            BookNestTheme {
                val bookViewModel: BookViewModel = viewModel(
                    factory = factory
                )

                AppNavGraph(
                    viewModel = bookViewModel
                )
            }
        }
    }
}