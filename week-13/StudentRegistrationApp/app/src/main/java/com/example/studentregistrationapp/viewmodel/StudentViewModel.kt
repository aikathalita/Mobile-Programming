package com.example.studentregistrationapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentregistrationapp.data.local.dao.SiswaDao
import com.example.studentregistrationapp.data.local.entity.Siswa
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StudentViewModel(
    private val siswaDao: SiswaDao
) : ViewModel() {

    val siswaList = siswaDao.getAllSiswa()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun tambahSiswa(nama: String, email: String, kelas: String) {
        viewModelScope.launch {
            siswaDao.insertSiswa(
                Siswa(
                    nama = nama,
                    email = email,
                    kelas = kelas
                )
            )
        }
    }

    fun editSiswa(siswa: Siswa) {
        viewModelScope.launch {
            siswaDao.updateSiswa(siswa)
        }
    }

    fun hapusSiswa(siswa: Siswa) {
        viewModelScope.launch {
            siswaDao.deleteSiswa(siswa)
        }
    }
}