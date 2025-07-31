package com.example.note.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.domain.usecase.CreateNoteUseCase
import com.example.note.domain.usecase.UpdateNoteUseCase
import com.example.note.presentation.model.NoteModel
import com.example.note.presentation.model.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val createNoteUseCase: CreateNoteUseCase
): ViewModel() {
    fun updateNote(noteModel: NoteModel) {
        viewModelScope.launch {
            updateNoteUseCase.invoke(noteModel.toEntity())
        }
    }
    fun createNote(noteModel: NoteModel) {
        viewModelScope.launch {
            createNoteUseCase.invoke(noteModel.toEntity())
        }
    }

}