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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.newspulseapp.data.model.Article
import com.example.newspulseapp.ui.components.NewsCard
import com.example.newspulseapp.viewmodel.NewsUiState
import com.example.newspulseapp.viewmodel.NewsViewModel

@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    onDetailClick: (Article) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F172A),
                        Color(0xFF1E3A8A),
                        Color(0xFFF8FAFC)
                    )
                )
            )
    ) {
        when (state) {
            is NewsUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                }
            }

            is NewsUiState.Success -> {
                val articles = (state as NewsUiState.Success).articles

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(18.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 8.dp)
                        ) {
                            Text(
                                text = "NewsPulse",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )

                            Text(
                                text = "Berita teknologi terbaru dari REST API",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFDDE7FF)
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            Text(
                                text = "Top Headlines",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

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

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Gagal memuat berita",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = message,
                        color = Color(0xFFDDE7FF)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            viewModel.loadTopHeadlines()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF38BDF8),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Coba Lagi")
                    }
                }
            }
        }
    }
}