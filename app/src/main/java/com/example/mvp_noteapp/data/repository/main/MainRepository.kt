package com.example.mvp_noteapp.data.repository.main

import com.example.mvp_noteapp.data.db.NoteDao
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: NoteDao) {

    fun showAll() = dao.getAllNote()
}