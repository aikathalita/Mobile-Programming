package com.example.newspulseapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.newspulseapp.data.model.Article
import com.example.newspulseapp.ui.components.NewsCard
import com.example.newspulseapp.viewmodel.NewsViewModel

@Composable
fun SavedScreen(
    viewModel: NewsViewModel,
    onDetailClick: (Article) -> Unit
) {
    val savedArticles = viewModel.savedArticles

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
        if (savedArticles.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Belum ada berita tersimpan",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Tekan ikon bookmark pada berita untuk menyimpannya.",
                    color = Color(0xFFDDE7FF)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Saved News",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Text(
                        text = "Berita yang kamu simpan selama aplikasi berjalan",
                        color = Color(0xFFDDE7FF),
                        modifier = Modifier.padding(top = 4.dp, bottom = 10.dp)
                    )
                }

                items(savedArticles) { article ->
                    NewsCard(
                        article = article,
                        isSaved = true,
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
    }
}