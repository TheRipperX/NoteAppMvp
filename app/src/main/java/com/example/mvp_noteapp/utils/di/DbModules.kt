package com.example.mvp_noteapp.utils.di

import android.content.Context
import androidx.room.Room
import com.example.mvp_noteapp.data.db.DataBaseNote
import com.example.mvp_noteapp.data.model.NoteEntity
import com.example.mvp_noteapp.utils.NOTE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModules {

    @Provides
    @Singleton
    fun proDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        DataBaseNote::class.java,
        NOTE_DATABASE
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    @Singleton
    fun proDao(dataBaseNote: DataBaseNote) = dataBaseNote.noteDao()

    @Provides
    @Singleton
    fun proEntity() = NoteEntity()
}