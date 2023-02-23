package com.hfad.notebook.ViewModel

import REPOSITORY
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