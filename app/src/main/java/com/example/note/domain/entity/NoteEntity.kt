package com.example.note.domain.entity

data class NoteEntity(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val summarize: String = "",
    val sentiment: Double = 0.5,
    val createdDate: String = ""
)