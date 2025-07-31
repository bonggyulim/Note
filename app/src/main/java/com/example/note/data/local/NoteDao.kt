package com.example.note.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.note.domain.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: _NoteEntity)
    @Update
    suspend fun update(note: _NoteEntity)
    @Delete
    suspend fun delete(note: _NoteEntity)
    @Query("SELECT * FROM _NoteEntity")
    fun getAll(): Flow<List<_NoteEntity>>
    @Query("SELECT * FROM _NoteEntity WHERE id = :id")
    fun getNote(id: String): Flow<_NoteEntity>
}