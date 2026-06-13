package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CalculatorTheme {
                Scaffold { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        CalculatorApp()
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorApp(modifier: Modifier = Modifier) {
    var firstNumber by remember { mutableStateOf("") }
    var secondNumber by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    fun calculate(operator: String) {
        val number1 = firstNumber.toDoubleOrNull()
        val number2 = secondNumber.toDoubleOrNull()

        if (number1 == null || number2 == null) {
            result = "Input tidak valid"
            return
        }

        result = when (operator) {
            "+" -> (number1 + number2).toString()
            "-" -> (number1 - number2).toString()
            "×" -> (number1 * number2).toString()
            "÷" -> {
                if (number2 == 0.0) {
                    "Tidak bisa dibagi 0"
                } else {
                    (number1 / number2).toString()
                }
            }
            else -> ""
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Kalkulator Sederhana",
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
        )

        OutlinedTextField(
            value = firstNumber,
            onValueChange = { firstNumber = it },
            label = { Text("Angka pertama") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = secondNumber,
            onValueChange = { secondNumber = it },
            label = { Text("Angka kedua") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = result,
            onValueChange = {},
            label = { Text("Hasil") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { calculate("+") },
                modifier = Modifier.padding(4.dp)
            ) {
                Text("+")
            }

            Button(
                onClick = { calculate("-") },
                modifier = Modifier.padding(4.dp)
            ) {
                Text("-")
            }

            Button(
                onClick = { calculate("×") },
                modifier = Modifier.padding(4.dp)
            ) {
                Text("×")
            }

            Button(
                onClick = { calculate("÷") },
                modifier = Modifier.padding(4.dp)
            ) {
                Text("÷")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                firstNumber = ""
                secondNumber = ""
                result = ""
            }
        ) {
            Text("Clear")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        CalculatorApp()
    }
}