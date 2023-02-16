package com.hfad.notebook.ViewModels.Add

import REPOSITORY
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.notebook.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddViewModel : ViewModel() {

    fun insert(note: Note, onSuccess:() -> Unit) =
        viewModelScope.launch (Dispatchers.IO ){
            REPOSITORY.insert(note){
                onSuccess()
            }
        }
}