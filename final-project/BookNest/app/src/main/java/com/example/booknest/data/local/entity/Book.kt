package com.example.booknest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    val category: String,
    val year: String,
    val description: String,
    val status: String = "AVAILABLE",
    val coverUri: String? = null,
    val coverColor: Long = 0xFF2563EB
)