package com.example.note.domain.usecase

import com.example.note.domain.entity.NoteEntity
import com.example.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAllNoteUseCase @Inject constructor(private val noteRepository: NoteRepository) {
    operator fun invoke () : Flow<List<NoteEntity>> {
        return noteRepository.readAllNote()
    }

}