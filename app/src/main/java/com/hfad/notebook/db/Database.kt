package com.hfad.notebook.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hfad.notebook.db.interfaces.Dao
import com.hfad.notebook.model.Note

@Database(entities = [Note::class], version = 3)
abstract class Database : RoomDatabase() {
    abstract fun getNoteDao(): Dao

    companion object {
        private var database: com.hfad.notebook.db.Database? = null

        @Synchronized
        fun getInstance(context: Context): com.hfad.notebook.db.Database {
            return if (database == null) {
                database =
                    Room.databaseBuilder(context, com.hfad.notebook.db.Database::class.java, "db_3")
                        .build()
                database as com.hfad.notebook.db.Database
            } else {
                database as com.hfad.notebook.db.Database
            }
        }
    }
}