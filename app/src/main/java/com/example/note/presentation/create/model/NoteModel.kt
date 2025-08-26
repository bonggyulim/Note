package com.example.note.presentation.create.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteModel(
    val id: Int,
    val title: String,
    val content: String,
    val summarize: String,
    val sentiment: Float,
    val createdDate: String
): Parcelable
