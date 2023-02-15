package com.hfad.notebook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_table")

class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo
    var title: String = "",

    @ColumnInfo
    var description: String = "",

    @ColumnInfo
    var creationTime: String = ""
        ) : Serializable