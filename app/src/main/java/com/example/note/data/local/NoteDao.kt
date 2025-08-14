package com.example.note.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM _NoteEntity ORDER BY id DESC")
    fun readAll(): Flow<List<_NoteEntity>>

    @Query("SELECT * FROM _NoteEntity WHERE id = :id")
    fun read(id: Int): Flow<_NoteEntity>

    @Upsert
    suspend fun upsertAll(items: List<_NoteEntity>)

    @Upsert
    suspend fun upsert(item: _NoteEntity)

    @Query("DELETE FROM _NoteEntity WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM _NoteEntity")
    suspend fun clear()

    @Transaction
    suspend fun replaceAll(items: List<_NoteEntity>) {
        clear()
        upsertAll(items)
    }

    @Query("SELECT COUNT(*) FROM _NoteEntity")
    suspend fun count(): Int

    @Query("DELETE FROM _NoteEntity WHERE id NOT IN (:ids)")
    suspend fun deleteAllExcept(ids: Set<Int>)
}