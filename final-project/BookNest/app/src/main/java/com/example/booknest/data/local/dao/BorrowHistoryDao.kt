package com.example.booknest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.booknest.data.local.entity.BorrowHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface BorrowHistoryDao {
    @Query("SELECT * FROM borrow_histories ORDER BY id DESC")
    fun getAllHistories(): Flow<List<BorrowHistory>>

    @Query("SELECT * FROM borrow_histories WHERE bookId = :bookId ORDER BY id DESC")
    fun getHistoriesByBookId(bookId: Int): Flow<List<BorrowHistory>>

    @Query("SELECT * FROM borrow_histories WHERE bookId = :bookId AND status = 'BORROWED' LIMIT 1")
    suspend fun getActiveBorrowByBookId(bookId: Int): BorrowHistory?

    @Insert
    suspend fun insertHistory(history: BorrowHistory)

    @Query("UPDATE borrow_histories SET status = 'RETURNED', returnDate = :returnDate WHERE id = :historyId")
    suspend fun markAsReturned(historyId: Int, returnDate: String)
}