package com.example.moneynotesapp

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.util.Calendar

private val FormCardColor = Color.White
private val PrimaryPurple = Color(0xFF8B5CF6)
private val PrimaryPink = Color(0xFFEC4899)
private val SoftLavender = Color(0xFFF3E8FF)
private val SoftPink = Color(0xFFFCE7F3)
private val DarkText = Color(0xFF2D1B3D)

@Composable
fun TransactionInputDialog(
    editingTransaction: MoneyTransaction?,
    onDismiss: () -> Unit,
    onAddTransaction: (MoneyTransaction) -> Unit,
    onUpdateTransaction: (MoneyTransaction) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        TransactionForm(
            modifier = Modifier.fillMaxWidth(),
            editingTransaction = editingTransaction,
            onAddTransaction = onAddTransaction,
            onUpdateTransaction = onUpdateTransaction,
            onCancelEdit = onDismiss
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionForm(
    modifier: Modifier = Modifier,
    editingTransaction: MoneyTransaction?,
    onAddTransaction: (MoneyTransaction) -> Unit,
    onUpdateTransaction: (MoneyTransaction) -> Unit,
    onCancelEdit: () -> Unit
) {
    val expenseCategories = listOf("Makan", "Transport", "Belanja", "Kuliah", "Lainnya")
    val incomeCategories = listOf("Uang Saku", "Gaji", "Bonus", "Hadiah", "Lainnya")

    var selectedType by remember { mutableStateOf(TransactionType.EXPENSE) }
    var selectedCategory by remember { mutableStateOf(expenseCategories.first()) }
    var amountText by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(todayText()) }
    var errorMessage by remember { mutableStateOf("") }

    val isEditing = editingTransaction != null

    LaunchedEffect(editingTransaction) {
        if (editingTransaction != null) {
            selectedType = editingTransaction.type
            selectedCategory = editingTransaction.category
            amountText = formatAmountInput(editingTransaction.amount.toString())
            noteText = if (editingTransaction.note == "-") "" else editingTransaction.note
            selectedDate = editingTransaction.date
            errorMessage = ""
        } else {
            selectedType = TransactionType.EXPENSE
            selectedCategory = expenseCategories.first()
            amountText = ""
            noteText = ""
            selectedDate = todayText()
            errorMessage = ""
        }
    }

    val activeCategories = if (selectedType == TransactionType.EXPENSE) {
        expenseCategories
    } else {
        incomeCategories
    }

    fun resetForm() {
        selectedType = TransactionType.EXPENSE
        selectedCategory = expenseCategories.first()
        amountText = ""
        noteText = ""
        selectedDate = todayText()
        errorMessage = ""
    }

    Card(
        modifier = modifier
            .heightIn(max = 640.dp)
            .verticalScroll(rememberScrollState()),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = FormCardColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Text(
                text = if (isEditing) "Edit Transaksi" else "Tambah Transaksi",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            TransactionTypeSelector(
                selectedType = selectedType,
                onSelectedTypeChanged = { newType ->
                    selectedType = newType

                    selectedCategory = if (newType == TransactionType.EXPENSE) {
                        expenseCategories.first()
                    } else {
                        incomeCategories.first()
                    }
                }
            )

            CategoryDropdownField(
                selectedCategory = selectedCategory,
                categories = activeCategories,
                onCategorySelected = { selectedCategory = it }
            )

            DatePickerField(
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it }
            )

            OutlinedTextField(
                value = amountText,
                onValueChange = { newValue ->
                    amountText = formatAmountInput(newValue)
                },
                label = { Text("Nominal") },
                placeholder = { Text("Contoh: 25.000") },
                prefix = { Text("Rp ") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                label = { Text("Catatan") },
                placeholder = { Text("Contoh: Makan siang") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color(0xFFDC2626),
                    fontSize = 13.sp
                )
            }

            Button(
                onClick = {
                    val amount = parseAmount(amountText)

                    if (amount == null || amount <= 0) {
                        errorMessage = "Nominal harus berupa angka lebih dari 0"
                        return@Button
                    }

                    val transaction = MoneyTransaction(
                        id = editingTransaction?.id ?: 0,
                        type = selectedType,
                        category = selectedCategory,
                        amount = amount,
                        note = noteText.ifBlank { "-" },
                        date = selectedDate
                    )

                    if (isEditing) {
                        onUpdateTransaction(transaction)
                    } else {
                        onAddTransaction(transaction)
                    }

                    resetForm()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryPurple
                )
            ) {
                Text(
                    text = if (isEditing) "Simpan Perubahan" else "Simpan Transaksi"
                )
            }

            TextButton(
                onClick = {
                    resetForm()
                    onCancelEdit()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Batal",
                    color = PrimaryPink,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun TransactionTypeSelector(
    selectedType: TransactionType,
    onSelectedTypeChanged: (TransactionType) -> Unit
) {
    androidx.compose.foundation.layout.Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Button(
            onClick = {
                onSelectedTypeChanged(TransactionType.EXPENSE)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedType == TransactionType.EXPENSE) {
                    PrimaryPink
                } else {
                    SoftPink
                },
                contentColor = if (selectedType == TransactionType.EXPENSE) {
                    Color.White
                } else {
                    PrimaryPink
                }
            ),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text("Pengeluaran")
        }

        Button(
            onClick = {
                onSelectedTypeChanged(TransactionType.INCOME)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedType == TransactionType.INCOME) {
                    PrimaryPurple
                } else {
                    SoftLavender
                },
                contentColor = if (selectedType == TransactionType.INCOME) {
                    Color.White
                } else {
                    PrimaryPurple
                }
            ),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text("Pemasukan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdownField(
    selectedCategory: String,
    categories: List<String>,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = selectedCategory,
            onValueChange = {},
            readOnly = true,
            label = {
                Text("Kategori")
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Text(category)
                    },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DatePickerField(
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = remember(selectedDate) {
        parseDateToCalendar(selectedDate)
    }

    OutlinedButton(
        onClick = {
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(year, month, dayOfMonth)

                    onDateSelected(formatCalendarDate(selectedCalendar))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = "Tanggal: $selectedDate",
            modifier = Modifier.fillMaxWidth()
        )
    }
}