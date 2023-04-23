package com.example.mvp_noteapp.data.repository.main

import com.example.mvp_noteapp.data.db.NoteDao
import com.example.mvp_noteapp.data.model.NoteEntity
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: NoteDao) {

    fun showAll() = dao.getAllNote()
    fun deleteNote(noteEntity: NoteEntity) = dao.deleteNote(noteEntity)
    fun filterNote(priority: String) = dao.filterNote(priority)
}