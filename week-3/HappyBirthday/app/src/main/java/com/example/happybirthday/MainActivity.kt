package com.example.happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happybirthday.ui.theme.HappyBirthdayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            HappyBirthdayTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BirthdayGreeting(
                        message = "Happy Birthday, Aika!",
                        from = "From: Thalita Aika Rahmani"
                    )
                }
            }
        }
    }
}

@Composable
fun BirthdayGreeting(message: String, from: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            fontSize = 42.sp,
            lineHeight = 48.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = from,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BirthdayGreetingPreview() {
    HappyBirthdayTheme {
        BirthdayGreeting(
            message = "Happy Birthday, Aika!",
            from = "From: Thalita Aika Rahmani"
        )
    }
}