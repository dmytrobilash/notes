package com.hfad.notebook.db

import androidx.lifecycle.LiveData
import com.hfad.notebook.model.Note

interface Repository {
    val allNotes:LiveData<List<Note>>
    suspend fun insert(note:Note, onSuccess:() -> Unit)
    suspend fun delete(note:Note, onSuccess:() -> Unit)
}