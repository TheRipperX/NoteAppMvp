package com.example.mvp_noteapp.ui.main

import com.example.mvp_noteapp.data.model.NoteEntity

interface MainContracts {

    interface View {
        fun showData(notes: List<NoteEntity>)
        fun emptyList()
    }

    interface Presenter {
        fun showAllNotes()
    }
}