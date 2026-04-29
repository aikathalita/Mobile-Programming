package com.example.moneynotesapp

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

private const val PREF_NAME = "money_notes_pref"
private const val TRANSACTIONS_KEY = "transactions"

fun saveTransactions(
    context: Context,
    transactions: List<MoneyTransaction>
) {
    val jsonArray = JSONArray()

    transactions.forEach { transaction ->
        val jsonObject = JSONObject().apply {
            put("id", transaction.id)
            put("type", transaction.type.name)
            put("category", transaction.category)
            put("amount", transaction.amount)
            put("note", transaction.note)
            put("date", transaction.date)
        }

        jsonArray.put(jsonObject)
    }

    val sharedPreferences = context.getSharedPreferences(
        PREF_NAME,
        Context.MODE_PRIVATE
    )

    sharedPreferences
        .edit()
        .putString(TRANSACTIONS_KEY, jsonArray.toString())
        .apply()
}

fun loadTransactions(context: Context): List<MoneyTransaction> {
    val sharedPreferences = context.getSharedPreferences(
        PREF_NAME,
        Context.MODE_PRIVATE
    )

    val jsonText = sharedPreferences.getString(TRANSACTIONS_KEY, null)
        ?: return emptyList()

    return try {
        val jsonArray = JSONArray(jsonText)
        val transactions = mutableListOf<MoneyTransaction>()

        for (index in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(index)

            val typeText = jsonObject.optString(
                "type",
                TransactionType.EXPENSE.name
            )

            val transactionType = try {
                TransactionType.valueOf(typeText)
            } catch (e: Exception) {
                TransactionType.EXPENSE
            }

            val transaction = MoneyTransaction(
                id = jsonObject.optInt("id"),
                type = transactionType,
                category = jsonObject.optString("category"),
                amount = jsonObject.optInt("amount"),
                note = jsonObject.optString("note"),
                date = jsonObject.optString("date")
            )

            transactions.add(transaction)
        }

        transactions
    } catch (e: Exception) {
        emptyList()
    }
}