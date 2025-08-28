package com.example.note.data.remote

data class NoteResponse(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val summarize: String? = null,
    val sentiment: Double? = null,
    val createdDate: String = ""
)
