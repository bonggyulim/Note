package com.example.note.data.repository

import com.example.note.data.local.NoteDao
import com.example.note.data.model.toDomain
import com.example.note.data.model.toEntity
import com.example.note.domain.entity.NoteEntity
import com.example.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {
    override suspend fun createNote(noteEntity: NoteEntity) {
        noteDao.insert(noteEntity.toEntity())
    }

    override suspend fun deleteNote(noteEntity: NoteEntity) {
        noteDao.delete(noteEntity.toEntity())
    }

    override suspend fun updateNote(noteEntity: NoteEntity) {
        noteDao.update(noteEntity.toEntity())
    }

    override fun readAllNote(): Flow<List<NoteEntity>> {
        return noteDao.getAll().map { flow -> flow.map { list -> list.toDomain() } }
    }

    override fun readNote(id: String): Flow<NoteEntity> {
        return noteDao.getNote(id).map { it.toDomain() }
    }
}