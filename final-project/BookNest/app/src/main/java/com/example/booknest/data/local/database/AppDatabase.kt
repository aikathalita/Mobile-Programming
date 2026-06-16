package com.example.booknest.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.booknest.data.local.dao.BookDao
import com.example.booknest.data.local.dao.BorrowHistoryDao
import com.example.booknest.data.local.entity.Book
import com.example.booknest.data.local.entity.BorrowHistory

@Database(
    entities = [
        Book::class,
        BorrowHistory::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun borrowHistoryDao(): BorrowHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "booknest_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}