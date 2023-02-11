package com.hfad.notebook.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.hfad.notebook.model.Note

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note_table")
    fun getAll():LiveData<List<Note>>





}