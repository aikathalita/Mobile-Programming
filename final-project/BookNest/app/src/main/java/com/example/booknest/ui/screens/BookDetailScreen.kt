package com.example.booknest.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.booknest.ui.components.BookCover
import com.example.booknest.ui.components.StatusChip
import com.example.booknest.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    viewModel: BookViewModel,
    bookId: Int,
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (Int) -> Unit,
    onNavigateToBorrow: (Int) -> Unit
) {
    val books by viewModel.books.collectAsState()
    val book = books.find { it.id == bookId }

    var showDeleteDialog by remember { mutableStateOf(false) }

    if (book == null) {
        EmptyDetailScreen(onNavigateBack = onNavigateBack)
        return
    }

    val isBorrowed = book.status == "BORROWED"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Detail Buku",
                        fontWeight = FontWeight.ExtraBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onNavigateToEdit(book.id)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Buku"
                        )
                    }

                    IconButton(
                        onClick = {
                            showDeleteDialog = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Hapus Buku",
                            tint = Color(0xFFDC2626)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF8EFE2),
                    titleContentColor = Color(0xFF111827),
                    navigationIconContentColor = Color(0xFF111827),
                    actionIconContentColor = Color(0xFF111827)
                )
            )
        },
        containerColor = Color(0xFFF7F2EA)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
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
                top = paddingValues.calculateTopPadding() + 12.dp,
                bottom = 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(36.dp),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        BookCover(
                            title = book.title,
                            author = book.author,
                            coverUri = book.coverUri,
                            coverColor = book.coverColor,
                            modifier = Modifier.size(width = 180.dp, height = 260.dp)
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = book.title,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF111827)
                            )

                            Text(
                                text = book.author,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF64748B)
                            )

                            StatusChip(status = book.status)
                        }
                    }
                }
            }

            item {
                DetailInfoCard(
                    category = book.category.ifBlank { "-" },
                    year = book.year.ifBlank { "-" },
                    description = book.description.ifBlank { "Belum ada deskripsi untuk buku ini." }
                )
            }

            item {
                if (isBorrowed) {
                    Button(
                        onClick = {
                            viewModel.returnBook(book)
                            onNavigateBack()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),
                        shape = RoundedCornerShape(22.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF16A34A),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardReturn,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            text = "Tandai Sudah Dikembalikan",
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            onNavigateToBorrow(book.id)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),
                        shape = RoundedCornerShape(22.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1D4ED8),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            text = "Catat Peminjaman",
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            title = {
                Text(text = "Hapus Buku?")
            },
            text = {
                Text(text = "Buku ini akan dihapus dari koleksi BookNest.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteBook(book)
                        showDeleteDialog = false
                        onNavigateBack()
                    }
                ) {
                    Text(
                        text = "Hapus",
                        color = Color(0xFFDC2626)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text(text = "Batal")
                }
            }
        )
    }
}

@Composable
fun DetailInfoCard(
    category: String,
    year: String,
    description: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        color = Color.White,
        shadowElevation = 7.dp
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Informasi Buku",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF111827)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DetailMiniCard(
                    label = "Kategori",
                    value = category,
                    icon = Icons.Default.Category,
                    modifier = Modifier.weight(1f)
                )

                DetailMiniCard(
                    label = "Tahun",
                    value = year,
                    icon = Icons.Default.CalendarMonth,
                    modifier = Modifier.weight(1f)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Deskripsi",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1D4ED8)
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF334155)
                )
            }
        }
    }
}

@Composable
fun DetailMiniCard(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFFF1F5F9)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF1D4ED8)
            )

            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF64748B)
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF111827)
            )
        }
    }
}

@Composable
fun EmptyDetailScreen(
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F2EA)),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 7.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoStories,
                    contentDescription = null,
                    tint = Color(0xFF94A3B8),
                    modifier = Modifier.size(42.dp)
                )

                Text(
                    text = "Buku tidak ditemukan",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold
                )

                OutlinedButton(
                    onClick = onNavigateBack
                ) {
                    Text(text = "Kembali")
                }
            }
        }
    }
}