package com.example.mvp_noteapp.base

import io.reactivex.rxjava3.disposables.Disposable

open class BasePresenterImpl: BasePresenter {

    var disposable: Disposable? = null

    override fun onStopApp() {
        disposable?.let {
            it.dispose()
        }
    }
}