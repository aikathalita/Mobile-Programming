package com.example.booknest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.booknest.ui.screens.AddEditBookScreen
import com.example.booknest.ui.screens.BookDetailScreen
import com.example.booknest.ui.screens.BorrowBookScreen
import com.example.booknest.ui.screens.HistoryScreen
import com.example.booknest.ui.screens.HomeScreen
import com.example.booknest.ui.screens.SplashScreen
import com.example.booknest.viewmodel.BookViewModel

@Composable
fun AppNavGraph(
    viewModel: BookViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToAddBook = {
                    navController.navigate(Screen.AddBook.route)
                },
                onNavigateToDetail = { bookId ->
                    navController.navigate(Screen.DetailBook.createRoute(bookId))
                },
                onNavigateToHistory = {
                    navController.navigate(Screen.History.route)
                }
            )
        }

        composable(Screen.AddBook.route) {
            AddEditBookScreen(
                viewModel = viewModel,
                bookId = null,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.DetailBook.route,
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0

            BookDetailScreen(
                viewModel = viewModel,
                bookId = bookId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToEdit = { id ->
                    navController.navigate(Screen.EditBook.createRoute(id))
                },
                onNavigateToBorrow = { id ->
                    navController.navigate(Screen.BorrowBook.createRoute(id))
                }
            )
        }

        composable(
            route = Screen.EditBook.route,
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0

            AddEditBookScreen(
                viewModel = viewModel,
                bookId = bookId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.BorrowBook.route,
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0

            BorrowBookScreen(
                viewModel = viewModel,
                bookId = bookId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.History.route) {
            HistoryScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}