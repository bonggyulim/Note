package com.example.note.domain.repository

import com.example.note.domain.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun createNote(noteEntity: NoteEntity): Result<Unit>
    suspend fun deleteNote(noteEntity: NoteEntity): Result<Unit>
    suspend fun updateNote(noteEntity: NoteEntity): Result<Unit>
    suspend fun readAllNote(): Result<List<NoteEntity>>
}