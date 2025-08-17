package com.example.note.presentation.create.model

import com.example.note.domain.entity.NoteEntity

fun NoteEntity.toModel(): NoteModel {
    return NoteModel(
        id,
        title,
        content,
        createdDate
    )
}

fun NoteModel.toEntity(): NoteEntity {
    return NoteEntity(
        id,
        title,
        content,
        createdDate
    )
}
