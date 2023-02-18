package com.hfad.notebook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_table")

class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name="description")
    var description: String = "",

    @ColumnInfo(name = "creation")
    var creationTime: String = "",

    @ColumnInfo(name = "priority")
    var taskPriority: Int = 0,

    @ColumnInfo(name = "finish")
    var finished: String = ""
        ): Serializable