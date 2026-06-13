package com.example.newspulseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.newspulseapp.navigation.AppNavGraph
import com.example.newspulseapp.ui.theme.NewsPulseAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewsPulseAppTheme {
                AppNavGraph()
            }
        }
    }
}