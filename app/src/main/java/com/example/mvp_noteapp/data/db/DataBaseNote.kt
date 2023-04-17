package com.example.mvp_noteapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvp_noteapp.data.model.NoteEntity
import com.example.mvp_noteapp.utils.DATABASE_VERSION

@Database(entities = [NoteEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class DataBaseNote: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}