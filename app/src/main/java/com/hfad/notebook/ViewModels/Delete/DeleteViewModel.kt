package com.hfad.notebook.ViewModels.Delete

import REPOSITORY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.notebook.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteViewModel : ViewModel() {
    fun delete(note: Note, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.delete(note) {
                onSuccess()
            }
        }
}