package com.hfad.notebook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "note_table")

class Note(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name="description")
    var description: String = "",

    @ColumnInfo(name = "creation")
    var creation: Long = 0,

    @ColumnInfo(name = "finished")
    var finished: Long = 0

    ): Serializable