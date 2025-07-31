package com.example.note.data.model

import com.example.note.data.local._NoteEntity
import com.example.note.domain.entity.NoteEntity

fun NoteResponse.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id,
        title,
        content,
        createdDate
    )
}

fun NoteEntity.toEntity(): _NoteEntity = _NoteEntity(id, title, content, createdDate)
fun _NoteEntity.toDomain(): NoteEntity = NoteEntity(id, title, content, createdDate)