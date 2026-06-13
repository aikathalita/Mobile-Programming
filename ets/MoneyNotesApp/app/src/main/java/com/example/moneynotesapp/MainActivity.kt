package com.example.moneynotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.TextButton

private val AppBackground = Color(0xFFFFF7FB)
private val SoftPurple = Color(0xFF8B5CF6)
private val SoftPink = Color(0xFFEC4899)
private val DarkText = Color(0xFF2D1B3D)
private val MutedText = Color(0xFF7C6A8A)
private val SoftLavender = Color(0xFFF3E8FF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MoneyNotesRootApp()
            }
        }
    }
}

@Composable
fun MoneyNotesRootApp() {
    var showHomePage by remember {
        mutableStateOf(true)
    }

    if (showHomePage) {
        HomePage(
            onStartClick = {
                showHomePage = false
            }
        )
    } else {
        MoneyNotesApp(
            onBackToHome = {
                showHomePage = true
            }
        )
    }
}

@Composable
fun MoneyNotesApp(
    onBackToHome: () -> Unit
) {
    val context = LocalContext.current

    val transactions = remember {
        mutableStateListOf<MoneyTransaction>()
    }

    LaunchedEffect(Unit) {
        val savedTransactions = loadTransactions(context)
        transactions.clear()
        transactions.addAll(savedTransactions)
    }

    var showTransactionDialog by remember {
        mutableStateOf(false)
    }

    var transactionBeingEdited by remember {
        mutableStateOf<MoneyTransaction?>(null)
    }

    var selectedFilter by remember {
        mutableStateOf(TransactionFilter.ALL)
    }

    var sortNewestFirst by remember {
        mutableStateOf(true)
    }

    val totalIncome = transactions
        .filter { it.type == TransactionType.INCOME }
        .sumOf { it.amount }

    val totalExpense = transactions
        .filter { it.type == TransactionType.EXPENSE }
        .sumOf { it.amount }

    val balance = totalIncome - totalExpense

    val filteredTransactions = transactions.filter { transaction ->
        when (selectedFilter) {
            TransactionFilter.ALL -> true
            TransactionFilter.INCOME -> transaction.type == TransactionType.INCOME
            TransactionFilter.EXPENSE -> transaction.type == TransactionType.EXPENSE
        }
    }

    val displayedTransactions = if (sortNewestFirst) {
        filteredTransactions.sortedWith(
            compareByDescending<MoneyTransaction> {
                dateSortValue(it.date)
            }.thenByDescending {
                it.id
            }
        )
    } else {
        filteredTransactions.sortedWith(
            compareBy<MoneyTransaction> {
                dateSortValue(it.date)
            }.thenBy {
                it.id
            }
        )
    }

    Scaffold(
        containerColor = AppBackground,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    transactionBeingEdited = null
                    showTransactionDialog = true
                },
                containerColor = SoftPurple,
                contentColor = Color.White
            ) {
                Text(
                    text = "+ Tambah",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(padding),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 90.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Dashboard",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )

                    TextButton(
                        onClick = onBackToHome
                    ) {
                        Text(
                            text = "Home",
                            color = SoftPurple,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                HeaderCard(
                    balance = balance,
                    income = totalIncome,
                    expense = totalExpense
                )
            }

            item {
                FinanceChart(
                    income = totalIncome,
                    expense = totalExpense
                )
            }

            item {
                FilterSortCard(
                    selectedFilter = selectedFilter,
                    sortNewestFirst = sortNewestFirst,
                    totalDisplayed = displayedTransactions.size,
                    onFilterChange = { selectedFilter = it },
                    onSortChange = { sortNewestFirst = it }
                )
            }

            item {
                Text(
                    text = "Daftar Transaksi (${displayedTransactions.size})",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
            }

            if (displayedTransactions.isEmpty()) {
                item {
                    EmptyTransactionCard()
                }
            } else {
                items(displayedTransactions, key = { it.id }) { transaction ->
                    TransactionCard(
                        transaction = transaction,
                        onEdit = {
                            transactionBeingEdited = transaction
                            showTransactionDialog = true
                        },
                        onDelete = {
                            transactions.remove(transaction)
                            saveTransactions(context, transactions)

                            if (transactionBeingEdited?.id == transaction.id) {
                                transactionBeingEdited = null
                                showTransactionDialog = false
                            }
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    if (showTransactionDialog) {
        TransactionInputDialog(
            editingTransaction = transactionBeingEdited,
            onDismiss = {
                showTransactionDialog = false
                transactionBeingEdited = null
            },
            onAddTransaction = { newTransaction ->
                transactions.add(
                    0,
                    newTransaction.copy(id = nextId(transactions))
                )

                saveTransactions(context, transactions)

                showTransactionDialog = false
                transactionBeingEdited = null
            },
            onUpdateTransaction = { updatedTransaction ->
                val index = transactions.indexOfFirst {
                    it.id == updatedTransaction.id
                }

                if (index != -1) {
                    transactions[index] = updatedTransaction
                    saveTransactions(context, transactions)
                }

                showTransactionDialog = false
                transactionBeingEdited = null
            }
        )
    }
}

@Composable
fun HeaderCard(
    balance: Int,
    income: Int,
    expense: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            SoftPurple,
                            SoftPink
                        )
                    )
                )
                .padding(22.dp)
        ) {
            Text(
                text = "Money Notes",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Catatan pemasukan dan pengeluaran harian",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Saldo Saat Ini",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp
            )

            Text(
                text = formatRupiah(balance),
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryMiniCard(
                    modifier = Modifier.weight(1f),
                    title = "Pemasukan",
                    value = formatRupiah(income)
                )

                SummaryMiniCard(
                    modifier = Modifier.weight(1f),
                    title = "Pengeluaran",
                    value = formatRupiah(expense)
                )
            }
        }
    }
}

@Composable
fun SummaryMiniCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    Column(
        modifier = modifier
            .background(
                color = Color.White.copy(alpha = 0.18f),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(14.dp)
    ) {
        Text(
            text = title,
            color = Color.White.copy(alpha = 0.88f),
            fontSize = 12.sp
        )

        Text(
            text = value,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun FilterSortCard(
    selectedFilter: TransactionFilter,
    sortNewestFirst: Boolean,
    totalDisplayed: Int,
    onFilterChange: (TransactionFilter) -> Unit,
    onSortChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Filter & Urutan",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Text(
                text = "Tampilkan",
                fontSize = 13.sp,
                color = MutedText
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterButton(
                    label = "Semua",
                    selected = selectedFilter == TransactionFilter.ALL,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onFilterChange(TransactionFilter.ALL)
                    }
                )

                FilterButton(
                    label = "Masuk",
                    selected = selectedFilter == TransactionFilter.INCOME,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onFilterChange(TransactionFilter.INCOME)
                    }
                )

                FilterButton(
                    label = "Keluar",
                    selected = selectedFilter == TransactionFilter.EXPENSE,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onFilterChange(TransactionFilter.EXPENSE)
                    }
                )
            }

            Text(
                text = "Urutkan tanggal",
                fontSize = 13.sp,
                color = MutedText
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterButton(
                    label = "Terbaru",
                    selected = sortNewestFirst,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onSortChange(true)
                    }
                )

                FilterButton(
                    label = "Terlama",
                    selected = !sortNewestFirst,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onSortChange(false)
                    }
                )
            }

            Text(
                text = "Menampilkan $totalDisplayed transaksi",
                fontSize = 12.sp,
                color = MutedText
            )
        }
    }
}

@Composable
fun FilterButton(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) {
                SoftPurple
            } else {
                SoftLavender
            },
            contentColor = if (selected) {
                Color.White
            } else {
                DarkText
            }
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (selected) 3.dp else 0.dp
        )
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )
    }
}