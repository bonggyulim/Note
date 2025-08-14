package com.example.note.data.repository

import com.example.note.data.local.NoteDao
import com.example.note.data.mapper.toDomain
import com.example.note.data.mapper.toDto
import com.example.note.data.mapper.toLocal
import com.example.note.data.remote.NoteApi
import com.example.note.domain.entity.NoteEntity
import com.example.note.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao,
    private val api: NoteApi,
) : NoteRepository {

    override suspend fun createNote(noteEntity: NoteEntity): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                val resp = api.create(noteEntity.toDto())
                if (!resp.isSuccessful) throw HttpException(resp)
                val all = api.getAll().map { it.toLocal() }
                if (all.isNotEmpty()) dao.upsertAll(all)
            }
        }

    override suspend fun updateNote(noteEntity: NoteEntity): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val resp = api.update(noteEntity.id, noteEntity.toDto())
            if (!resp.isSuccessful) throw HttpException(resp)
            val all = api.getAll().map { it.toLocal() }
            if (all.isNotEmpty()) dao.upsertAll(all)
        }
    }

    override suspend fun deleteNote(noteEntity: NoteEntity): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val resp = api.delete(noteEntity.id)
            if (!resp.isSuccessful) throw HttpException(resp)
            dao.deleteById(noteEntity.id)
        }
    }

    override fun readAllNote(): Flow<List<NoteEntity>> =
        dao.readAll().map { it.map { e -> e.toDomain() } }

    override suspend fun refreshAllFromRemote(): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val remote = api.getAll()
            if (remote.isEmpty()) return@runCatching
            dao.upsertAll(remote.map { it.toLocal() })
        }
    }
}
