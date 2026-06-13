package com.example.studentregistrationapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studentregistrationapp.data.local.dao.SiswaDao

class StudentViewModelFactory(
    private val siswaDao: SiswaDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            return StudentViewModel(siswaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}