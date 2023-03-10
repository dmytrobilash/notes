package com.hfad.notebook.db

import androidx.lifecycle.LiveData
import com.hfad.notebook.db.interfaces.Dao
import com.hfad.notebook.db.interfaces.Repository
import com.hfad.notebook.model.Note

class Realization(private val dao: Dao) : Repository {

    override val allNotes: LiveData<List<Note>>
        get() = dao.getAll()

    override val getAllCreationByAscending: LiveData<List<Note>>
        get() = dao.getAllCreationByAscending()

    override val getAllCreationByDescending: LiveData<List<Note>>
        get() = dao.getAllCreationByDescending()

    override val getAllFinishedByAscending: LiveData<List<Note>>
        get() = dao.getAllFinishedByAscending()

    override val getAllFinishedByDescending: LiveData<List<Note>>
        get() = dao.getAllFinishedByDescending()

    override suspend fun insert(note: Note, onSuccess: () -> Unit) {
        dao.insert(note)
        onSuccess()
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        dao.delete(note)
        onSuccess()
    }

    override suspend fun update(note:Note, onSuccess: () -> Unit) {
        dao.update(note.title, note.description, note.finished, note.id)
        onSuccess()
    }

}