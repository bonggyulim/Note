package com.example.note.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteModel(
    val id: Int,
    val title: String,
    val content: String,
    val createdDate: String
): Parcelable
