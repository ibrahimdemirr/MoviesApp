package com.ibrahimdemir.moviesapp.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    open var disposable = CompositeDisposable()
}