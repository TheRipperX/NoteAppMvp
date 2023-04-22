package com.example.mvp_noteapp.ui.main

import com.example.mvp_noteapp.base.BasePresenterImpl
import com.example.mvp_noteapp.data.model.NoteEntity
import com.example.mvp_noteapp.data.repository.main.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(private val repository: MainRepository, private val view: MainContracts.View)
    :MainContracts.Presenter, BasePresenterImpl(){

    override fun showAllNotes() {

        disposable = repository.showAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {
                    view.showData(it)
                }
                else {
                    view.emptyList()
                }
            }
    }

    override fun deleteNote(noteEntity: NoteEntity) {

        disposable = repository.deleteNote(noteEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.deleteNoteSuccess()
            }
    }

}