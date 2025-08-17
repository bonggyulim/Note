package com.example.note.data.repository

import android.util.Log
import com.example.note.data.mapper.toDomain
import com.example.note.data.mapper.toRequest
import com.example.note.data.remote.NoteApi
import com.example.note.domain.entity.NoteEntity
import com.example.note.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val api: NoteApi,
) : NoteRepository {

    override suspend fun createNote(noteEntity: NoteEntity): Result<Unit> = withContext(Dispatchers.IO) {
            runCatching {
                val response = api.create(noteEntity.toRequest())
                if (response.isSuccessful) {
                    Unit
                } else {
                    Log.e("API", "code=${response.code()}, error=${response.errorBody()?.string()}")
                    throw HttpException(response)
                }
            }
        }

    override suspend fun updateNote(noteEntity: NoteEntity): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val response = api.update(noteEntity.id?: 0, body = noteEntity.toRequest())
            if (response.isSuccessful) {
                Unit
            } else {
                Log.e("API", "code=${response.code()}, error=${response.errorBody()?.string()}")
                throw HttpException(response)
            }
        }
    }

    override suspend fun deleteNote(noteEntity: NoteEntity): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val response = api.delete(noteEntity.id?: 0)
            if (response.isSuccessful) {
                Unit
            } else {
                Log.e("API", "code=${response.code()}, error=${response.errorBody()?.string()}")
                throw HttpException(response)
            }
        }
    }

    override suspend fun readAllNote(): List<NoteEntity> = withContext(Dispatchers.IO) {
        val response = api.getAll()
        if (response.isSuccessful) {
            response.body().orEmpty().map { it.toDomain() }
        } else {
            Log.e("API", "code=${response.code()}, error=${response.errorBody()?.string()}")
            throw HttpException(response)
        }

    }
}
