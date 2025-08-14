package com.example.note.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "_NoteEntity")
data class _NoteEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    val content: String,
    val createdDate: String
)