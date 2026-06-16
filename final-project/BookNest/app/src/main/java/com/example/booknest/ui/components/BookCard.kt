package com.example.booknest.ui.components

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.booknest.data.local.entity.Book

@Composable
fun BookCard(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isBorrowed = book.status == "BORROWED"

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(34.dp),
        color = Color.White,
        shadowElevation = 7.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BookCover(
                title = book.title,
                author = book.author,
                coverUri = book.coverUri,
                coverColor = book.coverColor,
                modifier = Modifier.size(width = 98.dp, height = 142.dp)
            )

            Spacer(modifier = Modifier.width(18.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = book.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF111827),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = book.author,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF64748B),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color(0xFFF1F5F9)
                ) {
                    Text(
                        text = book.category.ifBlank { "Tanpa Kategori" },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF334155),
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                StatusChip(status = book.status)

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if (isBorrowed) {
                        "Buku sedang dipinjam"
                    } else {
                        "Buku siap dipinjamkan"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8)
                )
            }
        }
    }
}

@Composable
fun StatusChip(
    status: String,
    modifier: Modifier = Modifier
) {
    val isAvailable = status == "AVAILABLE"

    val backgroundColor = if (isAvailable) {
        Color(0xFFE8F9EE)
    } else {
        Color(0xFFFFF0E3)
    }

    val contentColor = if (isAvailable) {
        Color(0xFF16A34A)
    } else {
        Color(0xFFEA580C)
    }

    val text = if (isAvailable) {
        "Tersedia"
    } else {
        "Dipinjam"
    }

    val icon = if (isAvailable) {
        Icons.Default.CheckCircle
    } else {
        Icons.Default.Schedule
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(contentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(13.dp)
                )
            }

            Spacer(modifier = Modifier.width(7.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = contentColor,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}