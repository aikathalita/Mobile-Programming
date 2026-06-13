package com.example.newspulseapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.newspulseapp.data.model.Article
import com.example.newspulseapp.ui.components.NewsCard
import com.example.newspulseapp.ui.components.SearchBar
import com.example.newspulseapp.viewmodel.NewsUiState
import com.example.newspulseapp.viewmodel.NewsViewModel

@Composable
fun SearchScreen(
    viewModel: NewsViewModel,
    onDetailClick: (Article) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var query by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F172A),
                        Color(0xFF1E40AF),
                        Color(0xFFF8FAFC)
                    )
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "Search News",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Text(
                        text = "Cari berita berdasarkan topik yang kamu mau",
                        color = Color(0xFFDDE7FF)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    SearchBar(
                        query = query,
                        onQueryChange = { query = it },
                        onSearch = {
                            viewModel.searchNews(query)
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            viewModel.searchNews(query)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF38BDF8),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cari Berita")
                    }
                }
            }

            when (state) {
                is NewsUiState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.White)
                        }
                    }
                }

                is NewsUiState.Success -> {
                    val articles = (state as NewsUiState.Success).articles

                    if (articles.isEmpty()) {
                        item {
                            Text(
                                text = "Tidak ada berita yang ditemukan.",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    } else {
                        items(articles) { article ->
                            NewsCard(
                                article = article,
                                isSaved = viewModel.isArticleSaved(article),
                                onClick = {
                                    onDetailClick(article)
                                },
                                onSaveClick = {
                                    viewModel.toggleSavedArticle(article)
                                }
                            )
                        }
                    }
                }

                is NewsUiState.Error -> {
                    val message = (state as NewsUiState.Error).message

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Gagal mencari berita",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = message,
                                color = Color(0xFFDDE7FF)
                            )
                        }
                    }
                }
            }
        }
    }
}