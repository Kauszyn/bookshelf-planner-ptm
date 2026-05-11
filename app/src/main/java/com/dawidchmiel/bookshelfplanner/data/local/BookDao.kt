package com.dawidchmiel.bookshelfplanner.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY title COLLATE NOCASE ASC")
    fun observeBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    fun observeBook(id: String): Flow<BookEntity?>

    @Upsert
    suspend fun upsert(book: BookEntity)

    @Delete
    suspend fun delete(book: BookEntity)
}
