package com.example.note.domain.usecase

import com.example.note.domain.entity.NoteEntity
import com.example.note.domain.repository.NoteRepository
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(private val noteRepository: NoteRepository) {
    suspend operator fun invoke (noteEntity: NoteEntity): Result<Unit> {
        return noteRepository.createNote(noteEntity)
    }

}