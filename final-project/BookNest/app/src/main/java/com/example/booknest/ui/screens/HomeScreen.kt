package com.example.booknest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.booknest.ui.components.BookCard
import com.example.booknest.viewmodel.BookViewModel

@Composable
fun HomeScreen(
    viewModel: BookViewModel,
    onNavigateToAddBook: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToHistory: () -> Unit
) {
    val books by viewModel.books.collectAsState()
    var selectedFilter by remember { mutableStateOf(BookFilter.ALL) }

    val totalBooks = books.size
    val borrowedBooks = books.count { it.status == "BORROWED" }
    val availableBooks = books.count { it.status == "AVAILABLE" }

    val filteredBooks = when (selectedFilter) {
        BookFilter.ALL -> books
        BookFilter.AVAILABLE -> books.filter { it.status == "AVAILABLE" }
        BookFilter.BORROWED -> books.filter { it.status == "BORROWED" }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddBook,
                containerColor = Color(0xFF1D4ED8),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Buku"
                )
            }
        },
        containerColor = Color(0xFFF7F2EA)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF8EFE2),
                            Color(0xFFF7F2EA),
                            Color(0xFFEFF6FF)
                        )
                    )
                )
                .windowInsetsPadding(WindowInsets.statusBars)
                .windowInsetsPadding(WindowInsets.navigationBars),
            contentPadding = PaddingValues(
                start = 18.dp,
                end = 18.dp,
                top = 18.dp,
                bottom = 96.dp
            ),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                HomeHeroCard()
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    HomeStatCard(
                        title = "Total Buku",
                        value = totalBooks,
                        icon = Icons.Default.AutoStories,
                        color = Color(0xFF2563EB),
                        modifier = Modifier.weight(1f)
                    )

                    HomeStatCard(
                        title = "Tersedia",
                        value = availableBooks,
                        icon = Icons.Default.CheckCircle,
                        color = Color(0xFF16A34A),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                HomeStatCard(
                    title = "Sedang Dipinjam",
                    value = borrowedBooks,
                    icon = Icons.Default.Schedule,
                    color = Color(0xFFEA580C),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onNavigateToAddBook,
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF111827),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            text = "Tambah Buku",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        onClick = onNavigateToHistory,
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            text = "Riwayat",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                LibrarySectionHeader()
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterButton(
                        text = "Semua",
                        selected = selectedFilter == BookFilter.ALL,
                        onClick = { selectedFilter = BookFilter.ALL },
                        modifier = Modifier.weight(1f)
                    )

                    FilterButton(
                        text = "Tersedia",
                        selected = selectedFilter == BookFilter.AVAILABLE,
                        onClick = { selectedFilter = BookFilter.AVAILABLE },
                        modifier = Modifier.weight(1f)
                    )

                    FilterButton(
                        text = "Dipinjam",
                        selected = selectedFilter == BookFilter.BORROWED,
                        onClick = { selectedFilter = BookFilter.BORROWED },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (filteredBooks.isEmpty()) {
                item {
                    EmptyBookState(selectedFilter = selectedFilter)
                }
            } else {
                items(filteredBooks) { book ->
                    BookCard(
                        book = book,
                        onClick = {
                            onNavigateToDetail(book.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeHeroCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(36.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF111827),
                        Color(0xFF1E3A8A),
                        Color(0xFF2563EB)
                    )
                )
            )
            .padding(24.dp)
    ) {
        Box(
            modifier = Modifier
                .size(170.dp)
                .offset(x = 220.dp, y = (-55).dp)
                .alpha(0.15f)
                .clip(CircleShape)
                .background(Color.White)
        )

        Box(
            modifier = Modifier
                .size(110.dp)
                .offset(x = 250.dp, y = 145.dp)
                .alpha(0.12f)
                .clip(CircleShape)
                .background(Color.White)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color.White.copy(alpha = 0.16f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            tint = Color.White
                        )

                        Text(
                            text = "BookNest",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Halo, Aika",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Text(
                        text = "Kelola koleksi buku pribadi dan catat peminjaman dengan lebih rapi.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.88f)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeStatCard(
    title: String,
    value: Int,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        color = Color.White,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(color.copy(alpha = 0.13f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color
                )
            }

            Column {
                Text(
                    text = value.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF111827)
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF64748B)
                )
            }
        }
    }
}

@Composable
fun LibrarySectionHeader() {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Koleksi Buku",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF111827)
        )

        Text(
            text = "Pilih buku untuk melihat detail, mengedit, atau mencatat peminjaman.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF64748B)
        )
    }
}

@Composable
fun FilterButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val background = if (selected) {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFF111827),
                Color(0xFF2563EB)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                Color.White,
                Color.White
            )
        )
    }

    val textColor = if (selected) Color.White else Color(0xFF475569)

    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(50))
            .background(background)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.ExtraBold,
            color = textColor
        )
    }
}

@Composable
fun EmptyBookState(
    selectedFilter: BookFilter
) {
    val message = when (selectedFilter) {
        BookFilter.ALL -> "Belum ada buku yang ditambahkan."
        BookFilter.AVAILABLE -> "Belum ada buku yang tersedia."
        BookFilter.BORROWED -> "Belum ada buku yang sedang dipinjam."
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp),
        shape = RoundedCornerShape(32.dp),
        color = Color.White,
        shadowElevation = 5.dp
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = Color(0xFF94A3B8),
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Data Kosong",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF334155)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF64748B)
                )
            }
        }
    }
}

enum class BookFilter {
    ALL,
    AVAILABLE,
    BORROWED
}