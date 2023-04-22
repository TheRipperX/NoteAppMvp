package com.example.mvp_noteapp.ui.add

import com.example.mvp_noteapp.data.model.NoteEntity

interface NoteContracts {

    interface View {
        fun close()
        fun loadNote(n: NoteEntity)
    }

    interface Presenter {

        fun saveNoteApp(noteEntity: NoteEntity)
        fun getNote(id: Int)
        fun updateNote(noteEntity: NoteEntity)
    }
}