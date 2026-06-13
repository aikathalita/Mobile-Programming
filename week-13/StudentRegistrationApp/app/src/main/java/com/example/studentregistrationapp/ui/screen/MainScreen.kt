package com.example.studentregistrationapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.studentregistrationapp.data.local.entity.Siswa
import com.example.studentregistrationapp.ui.component.FormInput
import com.example.studentregistrationapp.ui.component.StudentItem
import com.example.studentregistrationapp.viewmodel.StudentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: StudentViewModel
) {
    val siswaList by viewModel.siswaList.collectAsState()

    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var kelas by remember { mutableStateOf("") }
    var selectedSiswa by remember { mutableStateOf<Siswa?>(null) }

    Scaffold(
        containerColor = Color(0xFFFFF7F0),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Registrasi Siswa",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF6C63FF)
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF7F0))
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Text(
                            text = if (selectedSiswa == null) {
                                "Form Registrasi Siswa"
                            } else {
                                "Edit Data Siswa"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2D2A55)
                        )

                        Text(
                            text = if (selectedSiswa == null) {
                                "Masukkan data siswa baru pada form berikut."
                            } else {
                                "Perbarui data siswa yang sudah dipilih."
                            },
                            color = Color(0xFF7A759A),
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        FormInput(
                            nama = nama,
                            email = email,
                            kelas = kelas,
                            buttonText = if (selectedSiswa == null) {
                                "Tambah Siswa"
                            } else {
                                "Simpan Perubahan"
                            },
                            onNamaChange = { nama = it },
                            onEmailChange = { email = it },
                            onKelasChange = { kelas = it },
                            onSubmit = {
                                if (selectedSiswa == null) {
                                    viewModel.tambahSiswa(
                                        nama = nama,
                                        email = email,
                                        kelas = kelas
                                    )
                                } else {
                                    viewModel.editSiswa(
                                        selectedSiswa!!.copy(
                                            nama = nama,
                                            email = email,
                                            kelas = kelas
                                        )
                                    )
                                    selectedSiswa = null
                                }

                                nama = ""
                                email = ""
                                kelas = ""
                            }
                        )

                        if (selectedSiswa != null) {
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedButton(
                                onClick = {
                                    selectedSiswa = null
                                    nama = ""
                                    email = ""
                                    kelas = ""
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text(
                                    text = "Batal Edit",
                                    color = Color(0xFF6C63FF)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Daftar Siswa",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D2A55)
                )
            }

            if (siswaList.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFE8CC)
                        )
                    ) {
                        Text(
                            text = "Belum ada data siswa yang terdaftar.",
                            color = Color(0xFF9A5A00),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            } else {
                items(siswaList) { siswa ->
                    StudentItem(
                        siswa = siswa,
                        onEdit = {
                            selectedSiswa = siswa
                            nama = siswa.nama
                            email = siswa.email
                            kelas = siswa.kelas
                        },
                        onDelete = {
                            viewModel.hapusSiswa(siswa)
                        }
                    )
                }
            }
        }
    }
}