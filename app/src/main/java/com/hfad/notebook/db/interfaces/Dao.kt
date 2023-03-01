package com.hfad.notebook.db.interfaces

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

    @Query("UPDATE note_table SET title = :title, description = :description, finished = :finished where id = :id")
    suspend fun update(title: String, description: String, finished: Long, id: Int)

    @Query("SELECT * FROM note_table")
    fun getAll():LiveData<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY finished  DESC")
    fun getAllByDescending(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY finished ASC")
    fun getAllByAsc(): LiveData<List<Note>>
}