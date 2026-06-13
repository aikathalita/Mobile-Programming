package com.example.moneynotesapp

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

enum class TransactionType {
    INCOME,
    EXPENSE
}

enum class TransactionFilter {
    ALL,
    INCOME,
    EXPENSE
}

data class MoneyTransaction(
    val id: Int,
    val type: TransactionType,
    val category: String,
    val amount: Int,
    val note: String,
    val date: String
)

private val indonesiaLocale = Locale("id", "ID")

fun nextId(transactions: List<MoneyTransaction>): Int {
    return (transactions.maxOfOrNull { it.id } ?: 0) + 1
}

fun todayText(): String {
    return formatDate(Date())
}

fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", indonesiaLocale)
    return formatter.format(date)
}

fun formatCalendarDate(calendar: Calendar): String {
    return formatDate(calendar.time)
}

fun parseDateToCalendar(dateText: String): Calendar {
    val calendar = Calendar.getInstance()
    val formatter = SimpleDateFormat("dd MMM yyyy", indonesiaLocale)

    return try {
        val date = formatter.parse(dateText)
        if (date != null) {
            calendar.time = date
        }
        calendar
    } catch (e: Exception) {
        Calendar.getInstance()
    }
}

fun dateSortValue(dateText: String): Long {
    val formatter = SimpleDateFormat("dd MMM yyyy", indonesiaLocale)

    return try {
        formatter.parse(dateText)?.time ?: 0L
    } catch (e: Exception) {
        0L
    }
}

fun formatRupiah(amount: Int): String {
    val formatter = NumberFormat.getCurrencyInstance(indonesiaLocale)
    return formatter.format(amount).replace(",00", "")
}

fun formatAmountInput(text: String): String {
    val digits = text.filter { it.isDigit() }.trimStart('0')

    if (digits.isEmpty()) {
        return ""
    }

    return digits
        .reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()
}

fun parseAmount(text: String): Int? {
    val digits = text.filter { it.isDigit() }

    if (digits.isEmpty()) {
        return null
    }

    return digits.toIntOrNull()
}