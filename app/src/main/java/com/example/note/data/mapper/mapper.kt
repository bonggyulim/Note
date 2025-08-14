package com.example.note.data.mapper

import com.example.note.data.local._NoteEntity
import com.example.note.data.remote.NoteResponse
import com.example.note.domain.entity.NoteEntity

fun _NoteEntity.toDomain(): NoteEntity = NoteEntity(id, title, content, createdDate)
fun NoteEntity.toDto(): NoteResponse = NoteResponse(id, title, content, createdDate)
fun NoteResponse.toLocal(): _NoteEntity = _NoteEntity(id, title, content, createdDate)
fun NoteEntity.toLocal(): _NoteEntity = _NoteEntity(id, title, content, createdDate)
fun NoteResponse.toDomain(): NoteEntity = NoteEntity(id, title, content, createdDate)