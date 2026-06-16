package com.example.booknest.data.repository

import com.example.booknest.data.local.dao.BookDao
import com.example.booknest.data.local.dao.BorrowHistoryDao
import com.example.booknest.data.local.entity.Book
import com.example.booknest.data.local.entity.BorrowHistory

class BookRepository(
    private val bookDao: BookDao,
    private val borrowHistoryDao: BorrowHistoryDao
) {
    val allBooks = bookDao.getAllBooks()
    val allHistories = borrowHistoryDao.getAllHistories()

    fun getBookById(bookId: Int) = bookDao.getBookById(bookId)

    fun getHistoriesByBookId(bookId: Int) =
        borrowHistoryDao.getHistoriesByBookId(bookId)

    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }

    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }

    suspend fun borrowBook(
        book: Book,
        borrowerName: String,
        borrowDate: String
    ) {
        val updatedBook = book.copy(status = "BORROWED")
        bookDao.updateBook(updatedBook)

        borrowHistoryDao.insertHistory(
            BorrowHistory(
                bookId = book.id,
                bookTitle = book.title,
                borrowerName = borrowerName,
                borrowDate = borrowDate,
                status = "BORROWED"
            )
        )
    }

    suspend fun returnBook(
        book: Book,
        returnDate: String
    ) {
        val activeHistory = borrowHistoryDao.getActiveBorrowByBookId(book.id)

        val updatedBook = book.copy(status = "AVAILABLE")
        bookDao.updateBook(updatedBook)

        if (activeHistory != null) {
            borrowHistoryDao.markAsReturned(
                historyId = activeHistory.id,
                returnDate = returnDate
            )
        }
    }
}