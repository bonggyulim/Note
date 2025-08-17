package com.example.note.data.mapper

import com.example.note.data.local._NoteEntity
import com.example.note.data.remote.NoteRequest
import com.example.note.data.remote.NoteResponse
import com.example.note.domain.entity.NoteEntity

fun NoteEntity.toRequest(): NoteRequest = NoteRequest(title = title, content = content, createdDate = createdDate)
fun NoteResponse.toDomain(): NoteEntity = NoteEntity(id, title, content, createdDate)

fun NoteEntity.toLocal(): _NoteEntity = _NoteEntity(id, title, content, createdDate)
fun _NoteEntity.toDomain(): NoteEntity = NoteEntity(id, title, content, createdDate)