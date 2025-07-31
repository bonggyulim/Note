package com.example.note.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class _NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val content: String,
    val createdDate: String
)