package com.example.newspulseapp.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newspulseapp.data.model.Article
import com.example.newspulseapp.viewmodel.NewsViewModel

@Composable
fun DetailScreen(
    article: Article,
    viewModel: NewsViewModel
) {
    val context = LocalContext.current
    val isSaved = viewModel.isArticleSaved(article)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xCC0F172A)
                            )
                        )
                    )
            )

            AssistChip(
                onClick = {},
                label = {
                    Text(
                        text = article.source?.name ?: "News",
                        color = Color.White
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color(0xCC2563EB)
                ),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(18.dp)
            )
        }

        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = article.title ?: "Tanpa Judul",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0F172A)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "${article.author ?: "Unknown Author"} • ${article.publishedAt?.take(10) ?: "-"}",
                color = Color(0xFF64748B),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = article.description ?: "Deskripsi berita tidak tersedia.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF334155)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = article.content ?: "Konten lengkap tidak tersedia dari API. Silakan buka sumber asli untuk membaca berita selengkapnya.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF475569)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.toggleSavedArticle(article)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSaved) Color(0xFFFFC857) else Color(0xFF2563EB),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Save"
                )
                Text(
                    text = if (isSaved) "Berita Disimpan" else "Simpan Berita",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = {
                    article.url?.let { url ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.OpenInNew,
                    contentDescription = "Open original",
                    tint = Color(0xFF2563EB)
                )
                Text(
                    text = "Buka Sumber Asli",
                    color = Color(0xFF2563EB),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}