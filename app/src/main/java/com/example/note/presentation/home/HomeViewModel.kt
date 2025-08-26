package com.example.note.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.domain.repository.NoteRepository
import com.example.note.presentation.UiState
import com.example.note.presentation.create.model.NoteModel
import com.example.note.presentation.create.model.toEntity
import com.example.note.presentation.create.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _notesState = MutableStateFlow<UiState<List<NoteModel>>>(UiState.Loading)
    val notesState: StateFlow<UiState<List<NoteModel>>> = _notesState

    var sort: Boolean = false

    fun getAllNote() {
        viewModelScope.launch {
            _notesState.value = UiState.Loading
            noteRepository.readAllNote()
                .onSuccess { list ->
                    sort = false
                    _notesState.value = UiState.Success(list.map { it.toModel() })
                }
                .onFailure { e ->
                    _notesState.value = UiState.Error(e.message.toString())
                }
        }
    }

    fun getAllNoteAscending() {
        viewModelScope.launch {
            noteRepository.readAllNote()
                .onSuccess { list ->
                    sort = true
                    _notesState.value = UiState.Success(list.map { it.toModel() }.sortedBy { it.title })
                }
                .onFailure { e ->
                    _notesState.value = UiState.Error(e.message.toString())
                }
        }
    }

    fun deleteNote(noteModel: NoteModel) {
        viewModelScope.launch {
            noteRepository.deleteNote(noteModel.toEntity())
                .onSuccess {
                    val cur = (_notesState.value as? UiState.Success)?.data.orEmpty()
                    val after = cur.filterNot { it.id == noteModel.id }
                    _notesState.value = UiState.Success(after)

                    noteRepository.readAllNote()
                        .onSuccess { list ->
                            _notesState.value = UiState.Success(list.map { it.toModel() })
                        }
                        .onFailure { e ->
                            _notesState.value = UiState.Error(e.message.toString())
                        }
                }
                .onFailure { e ->
                    _notesState.value = UiState.Error(e.message.toString())
                }
        }
    }
}