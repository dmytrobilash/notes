package com.hfad.notebook.ViewModels.Edit

import REPOSITORY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.notebook.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditViewModel : ViewModel() {
    fun update(note: Note, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.update(note) {
                onSuccess()
            }
        }
}