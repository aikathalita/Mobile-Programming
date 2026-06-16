package com.example.booknest.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object AddBook : Screen("add_book")
    data object History : Screen("history")

    data object DetailBook : Screen("detail_book/{bookId}") {
        fun createRoute(bookId: Int): String {
            return "detail_book/$bookId"
        }
    }

    data object EditBook : Screen("edit_book/{bookId}") {
        fun createRoute(bookId: Int): String {
            return "edit_book/$bookId"
        }
    }

    data object BorrowBook : Screen("borrow_book/{bookId}") {
        fun createRoute(bookId: Int): String {
            return "borrow_book/$bookId"
        }
    }
}