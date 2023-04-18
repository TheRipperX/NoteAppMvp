package com.example.mvp_noteapp.ui.add

import com.example.mvp_noteapp.data.model.NoteEntity

interface NoteContracts {

    interface View {
        fun close()
    }

    interface Presenter {

        fun saveNoteApp(noteEntity: NoteEntity)
    }
}