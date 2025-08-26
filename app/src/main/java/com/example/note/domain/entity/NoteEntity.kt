package com.example.note.domain.entity

data class NoteEntity(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val summarize: String = "",
    val sentiment: Float = 0.0f,
    val createdDate: String = ""
)