package com.hfad.notebook.ViewModel

import REPOSITORY
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hfad.notebook.db.Database
import com.hfad.notebook.db.Realization
import com.hfad.notebook.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StartViewModel(application: Application) : AndroidViewModel(application) {

    val context = application

    fun initDataBase(){
        val daoNote = Database.getInstance(context).getNoteDao()
        REPOSITORY = Realization(daoNote)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return REPOSITORY.allNotes
    }

    fun getAllCreationByAscending(): LiveData<List<Note>>{
        return REPOSITORY.getAllCreationByAscending
    }

    fun getAllCreationByDescending(): LiveData<List<Note>>{
        return REPOSITORY.getAllCreationByDescending
    }

    fun getAllFinishedByAscending(): LiveData<List<Note>>{
        return REPOSITORY.getAllFinishedByAscending
    }

    fun getAllFinishedByDescending(): LiveData<List<Note>>{
        return REPOSITORY.getAllFinishedByDescending
    }

    fun delete(note: Note, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.delete(note) {
                onSuccess()
            }
        }

}