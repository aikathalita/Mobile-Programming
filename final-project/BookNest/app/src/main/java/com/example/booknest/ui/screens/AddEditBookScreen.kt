package com.example.booknest.ui.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.booknest.ui.components.BookCover
import com.example.booknest.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditBookScreen(
    viewModel: BookViewModel,
    bookId: Int? = null,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val books by viewModel.books.collectAsState()
    val existingBook = books.find { it.id == bookId }
    val isEditMode = bookId != null

    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var coverUri by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf("") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            coverUri = it.toString()
        }
    }

    LaunchedEffect(existingBook?.id) {
        if (existingBook != null) {
            title = existingBook.title
            author = existingBook.author
            category = existingBook.category
            year = existingBook.year
            description = existingBook.description
            coverUri = existingBook.coverUri
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (isEditMode) "Edit Buku" else "Tambah Buku",
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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF8EFE2),
                    titleContentColor = Color(0xFF111827),
                    navigationIconContentColor = Color(0xFF111827)
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
                AddEditHeader(
                    isEditMode = isEditMode
                )
            }

            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(34.dp),
                    color = Color.White,
                    shadowElevation = 7.dp
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        BookCover(
                            title = title,
                            author = author,
                            coverUri = coverUri,
                            coverColor = existingBook?.coverColor ?: 0xFF2563EB,
                            modifier = Modifier.size(width = 155.dp, height = 225.dp)
                        )

                        Button(
                            onClick = {
                                imagePickerLauncher.launch(arrayOf("image/*"))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF111827),
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Image,
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.size(8.dp))

                            Text(
                                text = if (coverUri == null) "Pilih Cover Buku" else "Ganti Cover Buku",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(34.dp),
                    color = Color.White,
                    shadowElevation = 7.dp
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "Informasi Buku",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF111827)
                        )

                        BookTextField(
                            value = title,
                            onValueChange = {
                                title = it
                                errorMessage = ""
                            },
                            label = "Judul Buku",
                            leadingIcon = Icons.Default.Title
                        )

                        BookTextField(
                            value = author,
                            onValueChange = {
                                author = it
                                errorMessage = ""
                            },
                            label = "Penulis",
                            leadingIcon = Icons.Default.Person
                        )

                        BookTextField(
                            value = category,
                            onValueChange = {
                                category = it
                            },
                            label = "Kategori",
                            leadingIcon = Icons.Default.Category,
                            placeholder = "Novel, self improvement, teknologi, dll."
                        )

                        BookTextField(
                            value = year,
                            onValueChange = {
                                year = it
                            },
                            label = "Tahun Terbit",
                            leadingIcon = Icons.Default.CalendarMonth,
                            keyboardType = KeyboardType.Number
                        )

                        OutlinedTextField(
                            value = description,
                            onValueChange = {
                                description = it
                            },
                            label = {
                                Text("Deskripsi")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Description,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(132.dp),
                            shape = RoundedCornerShape(18.dp),
                            maxLines = 5
                        )

                        if (errorMessage.isNotBlank()) {
                            Text(
                                text = errorMessage,
                                color = Color(0xFFDC2626),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        if (title.isBlank() || author.isBlank()) {
                            errorMessage = "Judul dan penulis tidak boleh kosong."
                            return@Button
                        }

                        if (isEditMode && existingBook != null) {
                            viewModel.updateBook(
                                existingBook.copy(
                                    title = title,
                                    author = author,
                                    category = category,
                                    year = year,
                                    description = description,
                                    coverUri = coverUri
                                )
                            )
                        } else {
                            viewModel.addBook(
                                title = title,
                                author = author,
                                category = category,
                                year = year,
                                description = description,
                                coverUri = coverUri
                            )
                        }

                        onNavigateBack()
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
                        imageVector = Icons.Default.Save,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = if (isEditMode) "Simpan Perubahan" else "Simpan Buku",
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}

@Composable
fun AddEditHeader(
    isEditMode: Boolean
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        color = Color(0xFF111827),
        shadowElevation = 7.dp
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF111827),
                            Color(0xFF1E3A8A),
                            Color(0xFF2563EB)
                        )
                    )
                )
                .padding(22.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = if (isEditMode) "Perbarui Data Buku" else "Tambahkan Buku Baru",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Text(
                    text = if (isEditMode) {
                        "Ubah informasi buku agar koleksimu tetap rapi."
                    } else {
                        "Isi detail buku dan tambahkan cover dari galeri."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.86f)
                )
            }
        }
    }
}

@Composable
fun BookTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    placeholder: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(label)
        },
        placeholder = {
            if (placeholder != null) {
                Text(placeholder)
            }
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(18.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}