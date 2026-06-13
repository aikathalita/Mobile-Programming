package com.example.studentregistrationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studentregistrationapp.data.local.database.AppDatabase
import com.example.studentregistrationapp.ui.screen.MainScreen
import com.example.studentregistrationapp.ui.theme.StudentRegistrationAppTheme
import com.example.studentregistrationapp.viewmodel.StudentViewModel
import com.example.studentregistrationapp.viewmodel.StudentViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val siswaDao = database.siswaDao()
        val factory = StudentViewModelFactory(siswaDao)

        setContent {
            StudentRegistrationAppTheme {
                val studentViewModel: StudentViewModel = viewModel(factory = factory)
                MainScreen(viewModel = studentViewModel)
            }
        }
    }
}