package com.example.note.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.domain.repository.NoteRepository
import com.example.note.presentation.model.NoteModel
import com.example.note.presentation.model.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    fun createNote(noteModel: NoteModel) = viewModelScope.launch {
        noteRepository.createNote(noteModel.toEntity())
    }

    fun updateNote(noteModel: NoteModel) = viewModelScope.launch {
        noteRepository.updateNote(noteModel.toEntity())
    }
}