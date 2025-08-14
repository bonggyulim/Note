package com.example.note.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.domain.entity.NoteEntity
import com.example.note.domain.repository.NoteRepository
import com.example.note.presentation.UiState
import com.example.note.presentation.model.NoteModel
import com.example.note.presentation.model.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository

): ViewModel() {
    val readAllNoteUiState: StateFlow<UiState<List<NoteEntity>>> =
        noteRepository.readAllNote()
            .map<List<NoteEntity>, UiState<List<NoteEntity>>> { UiState.Success(it) }
            .onStart { emit(UiState.Loading) }
            .catch { emit(UiState.Error(it.message ?: "Unknown error")) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.Loading)


    init {
        viewModelScope.launch { noteRepository.refreshAllFromRemote() }
    }

    fun deleteNote(noteModel: NoteModel) {
        viewModelScope.launch {
            noteRepository.deleteNote(noteModel.toEntity())
        }
    }

}