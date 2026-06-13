package com.example.newspulseapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.newspulseapp.data.model.Article

@Composable
fun NewsCard(
    article: Article,
    isSaved: Boolean,
    onClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            Box {
                SubcomposeAsyncImage(
                    model = article.urlToImage,
                    contentDescription = article.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .clip(RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)),
                    contentScale = ContentScale.Crop,
                    loading = {
                        ImagePlaceholder(
                            text = "Loading Image..."
                        )
                    },
                    error = {
                        ImagePlaceholder(
                            text = "No Image Available"
                        )
                    }
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0x990B132B)
                                )
                            )
                        )
                )

                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            text = article.source?.name ?: "News",
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color(0xCC2563EB)
                    ),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                )

                IconButton(
                    onClick = onSaveClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = if (isSaved) {
                            Icons.Filled.Bookmark
                        } else {
                            Icons.Outlined.BookmarkBorder
                        },
                        contentDescription = "Save news",
                        tint = if (isSaved) Color(0xFFFFC857) else Color.White
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = article.title ?: "Tanpa Judul",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = article.description ?: "Deskripsi berita tidak tersedia.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF64748B),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = article.author ?: "Unknown author",
                        color = Color(0xFF2563EB),
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = article.publishedAt?.take(10) ?: "-",
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        }
    }
}

@Composable
fun ImagePlaceholder(
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF1E3A8A),
                        Color(0xFF2563EB),
                        Color(0xFF38BDF8)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}