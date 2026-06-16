package com.example.booknest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "borrow_histories")
data class BorrowHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bookId: Int,
    val bookTitle: String,
    val borrowerName: String,
    val borrowDate: String,
    val returnDate: String? = null,
    val status: String = "BORROWED"
)