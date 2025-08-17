package com.example.note.data.remote

data class NoteRequest(
    val title: String = "",
    val content: String = "",
    val createdDate: String = ""
)
