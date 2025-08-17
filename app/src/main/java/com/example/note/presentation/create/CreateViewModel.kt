package com.example.note.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.domain.repository.NoteRepository
import com.example.note.presentation.create.model.NoteModel
import com.example.note.presentation.create.model.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _successEvent = MutableSharedFlow<Unit>()
    val successEvent = _successEvent

    fun createNote(noteModel: NoteModel) = viewModelScope.launch {
        val result = noteRepository.createNote(noteModel.toEntity())
        if (result.isSuccess) {
            _successEvent.emit(Unit)
        }
    }

    fun updateNote(noteModel: NoteModel) = viewModelScope.launch {
        val result = noteRepository.updateNote(noteModel.toEntity())
        if (result.isSuccess) {
            _successEvent.emit(Unit)
        }
    }
}