package com.example.newspulseapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newspulseapp.data.model.Article
import com.example.newspulseapp.ui.screens.DetailScreen
import com.example.newspulseapp.ui.screens.HomeScreen
import com.example.newspulseapp.ui.screens.SavedScreen
import com.example.newspulseapp.ui.screens.SearchScreen
import com.example.newspulseapp.viewmodel.NewsViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val viewModel: NewsViewModel = viewModel()
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != "detail") {
                NavigationBar(
                    containerColor = Color(0xFF0F172A),
                    contentColor = Color.White
                ) {
                    NavigationBarItem(
                        selected = currentRoute == "home",
                        onClick = {
                            navController.navigate("home") {
                                popUpTo("home") { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home"
                            )
                        },
                        label = { Text("Home") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF38BDF8),
                            selectedTextColor = Color(0xFF38BDF8),
                            unselectedIconColor = Color.White,
                            unselectedTextColor = Color.White,
                            indicatorColor = Color(0xFF1E3A8A)
                        )
                    )

                    NavigationBarItem(
                        selected = currentRoute == "search",
                        onClick = {
                            navController.navigate("search") {
                                popUpTo("home") { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                        label = { Text("Search") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF38BDF8),
                            selectedTextColor = Color(0xFF38BDF8),
                            unselectedIconColor = Color.White,
                            unselectedTextColor = Color.White,
                            indicatorColor = Color(0xFF1E3A8A)
                        )
                    )

                    NavigationBarItem(
                        selected = currentRoute == "saved",
                        onClick = {
                            navController.navigate("saved") {
                                popUpTo("home") { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = "Saved"
                            )
                        },
                        label = { Text("Saved") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF38BDF8),
                            selectedTextColor = Color(0xFF38BDF8),
                            unselectedIconColor = Color.White,
                            unselectedTextColor = Color.White,
                            indicatorColor = Color(0xFF1E3A8A)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onDetailClick = { article ->
                        selectedArticle = article
                        navController.navigate("detail")
                    }
                )
            }

            composable("search") {
                SearchScreen(
                    viewModel = viewModel,
                    onDetailClick = { article ->
                        selectedArticle = article
                        navController.navigate("detail")
                    }
                )
            }

            composable("saved") {
                SavedScreen(
                    viewModel = viewModel,
                    onDetailClick = { article ->
                        selectedArticle = article
                        navController.navigate("detail")
                    }
                )
            }

            composable("detail") {
                selectedArticle?.let { article ->
                    DetailScreen(
                        article = article,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}