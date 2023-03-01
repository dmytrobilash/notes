package com.hfad.notebook.db.interfaces

import androidx.lifecycle.LiveData
import com.hfad.notebook.model.Note

interface Repository {

    val allNotes:LiveData<List<Note>>
    val getAllFinishedByAscending: LiveData<List<Note>>
    val getAllFinishedByDescending: LiveData<List<Note>>
    val getAllCreationByAscending: LiveData<List<Note>>
    val getAllCreationByDescending: LiveData<List<Note>>

    suspend fun insert(note:Note, onSuccess:() -> Unit)
    suspend fun delete(note:Note, onSuccess:() -> Unit)
    suspend fun update(note:Note, onSuccess: () -> Unit)
}