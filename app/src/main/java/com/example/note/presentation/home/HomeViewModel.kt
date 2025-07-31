package com.example.note.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.domain.entity.NoteEntity
import com.example.note.domain.repository.NoteRepository
import com.example.note.presentation.UiState
import com.example.note.presentation.model.NoteModel
import com.example.note.presentation.model.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository

): ViewModel() {
    private val _readAllNoteUiState = MutableStateFlow<UiState<List<NoteEntity>>>(UiState.Loading)
    val readAllNoteUiState : StateFlow<UiState<List<NoteEntity>>> = _readAllNoteUiState


    init {
        fetchNotes()
    }

    fun fetchNotes() {
        viewModelScope.launch {
            noteRepository.readAllNote()
                .catch { e -> _readAllNoteUiState.value = UiState.Error(e.message ?: "Unknown error") }
                .collect { notes -> _readAllNoteUiState.value = UiState.Success(notes) }
        }
    }
    fun deleteNote(noteModel: NoteModel) {
        viewModelScope.launch {
            noteRepository.deleteNote(noteModel.toEntity())
        }
    }

}