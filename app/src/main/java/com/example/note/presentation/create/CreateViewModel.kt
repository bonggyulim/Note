package com.example.note.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.domain.repository.NoteRepository
import com.example.note.presentation.UiState
import com.example.note.presentation.model.NoteModel
import com.example.note.presentation.model.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _createState = MutableStateFlow<UiState<Unit>?>(null)
    val createState: StateFlow<UiState<Unit>?> = _createState

    fun createNote(noteModel: NoteModel) = viewModelScope.launch {
        _createState.value = UiState.Loading
        val result = noteRepository.createNote(noteModel.toEntity())

        _createState.value = result.fold(
            onSuccess = { UiState.Success(Unit) },
            onFailure = { UiState.Error(it.message ?: "Create failed") }
        )
    }

    fun updateNote(noteModel: NoteModel) = viewModelScope.launch {
        _createState.value = UiState.Loading
        val result = noteRepository.updateNote(noteModel.toEntity())
        _createState.value = result.fold(
            onSuccess = { UiState.Success(Unit) },
            onFailure = { UiState.Error(it.message ?: "Update failed") }
        )
    }
}