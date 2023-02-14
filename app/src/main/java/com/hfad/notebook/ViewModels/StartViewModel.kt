package com.hfad.notebook.ViewModels

import REPOSITORY
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.hfad.notebook.db.Database
import com.hfad.notebook.db.Realization
import com.hfad.notebook.db.Repository
import com.hfad.notebook.model.Note

class StartViewModel(application: Application) : AndroidViewModel(application) {

    val context = application

    fun initDataBase(){
        val daoNote = Database.getInstance(context).getNoteDao()
        REPOSITORY = Realization(daoNote)
    }
    fun getAllNotes(): LiveData<List<Note>> {
        return REPOSITORY.allNotes
    }


}