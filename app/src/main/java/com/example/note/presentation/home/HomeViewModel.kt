package com.example.note.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.domain.repository.NoteRepository
import com.example.note.presentation.create.model.NoteModel
import com.example.note.presentation.create.model.toEntity
import com.example.note.presentation.create.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository
): ViewModel() {
    private val _notes = MutableLiveData<List<NoteModel>>()
    val notes: LiveData<List<NoteModel>> = _notes
    var sort: Boolean = false

    fun getAllNote() {
        viewModelScope.launch {
            sort = false
            val list = noteRepository.readAllNote().map { it.toModel() }
            _notes.value = list
        }
    }

    fun getAllNoteAscending() {
        viewModelScope.launch {
            sort = true
            val list = noteRepository.readAllNote().map { it.toModel() }
            _notes.value = list.sortedBy { it.title  }
        }
    }


    fun deleteNote(noteModel: NoteModel) {
        viewModelScope.launch {
            noteRepository.deleteNote(noteModel.toEntity())
            val list = noteRepository.readAllNote().map { it.toModel() }
            _notes.value = list
        }
    }

}