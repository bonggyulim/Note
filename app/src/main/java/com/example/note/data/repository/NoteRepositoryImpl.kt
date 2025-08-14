package com.example.note.data.repository

import com.example.note.data.mapper.toDomain
import com.example.note.data.mapper.toDto
import com.example.note.data.remote.NoteApi
import com.example.note.domain.entity.NoteEntity
import com.example.note.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val api: NoteApi,
) : NoteRepository {

    override suspend fun createNote(noteEntity: NoteEntity): Result<Unit> = withContext(Dispatchers.IO) {
            runCatching {
                api.create(noteEntity.toDto())
            }
        }

    override suspend fun updateNote(noteEntity: NoteEntity): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            api.update(noteEntity.id, body = noteEntity.toDto())
        }
    }

    override suspend fun deleteNote(noteEntity: NoteEntity): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            api.delete(noteEntity.id)
        }
    }

    override suspend fun readAllNote(): List<NoteEntity> {
        return api.getAll().map { it.toDomain() }
    }
}
