package com.example.mvp_noteapp.data.repository.add

import com.example.mvp_noteapp.data.db.NoteDao
import com.example.mvp_noteapp.data.model.NoteEntity
import javax.inject.Inject


class AddNoteRepository @Inject constructor(private val dao: NoteDao){

    fun saveNote(noteEntity: NoteEntity) = dao.saveNote(noteEntity)
    fun getNote(id: Int) = dao.getNote(id)
    fun updateNote(noteEntity: NoteEntity) = dao.updateNote(noteEntity)

}