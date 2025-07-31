package com.example.note.domain.repository

import com.example.note.domain.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun createNote(noteEntity: NoteEntity)
    suspend fun deleteNote(noteEntity: NoteEntity)
    suspend fun updateNote(noteEntity: NoteEntity)
    fun readAllNote() : Flow<List<NoteEntity>>
    fun readNote(id: String) : Flow<NoteEntity>
}