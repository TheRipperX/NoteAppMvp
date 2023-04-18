package com.example.mvp_noteapp.ui.add

import com.example.mvp_noteapp.base.BasePresenterImpl
import com.example.mvp_noteapp.data.model.NoteEntity
import com.example.mvp_noteapp.data.repository.add.AddNoteRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NotePresenter @Inject constructor(private val addNoteRepository: AddNoteRepository, private val view: NoteContracts.View)
    : NoteContracts.Presenter, BasePresenterImpl(){

    override fun saveNoteApp(noteEntity: NoteEntity) {

        disposable = addNoteRepository.saveNote(noteEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.close()
            }
    }
}