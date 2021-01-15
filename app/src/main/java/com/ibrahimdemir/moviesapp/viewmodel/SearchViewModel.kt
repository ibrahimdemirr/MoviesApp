package com.ibrahimdemir.moviesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ibrahimdemir.moviesapp.base.BaseViewModel
import com.ibrahimdemir.moviesapp.model.MovieSearchResponse
import com.ibrahimdemir.moviesapp.network.RetrofitFactory
import com.ibrahimdemir.moviesapp.util.ApiConstants.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SearchViewModel : BaseViewModel() {

    private val dataService = RetrofitFactory()

    val movieSearchResponse = MutableLiveData<MovieSearchResponse>()
    val dataSuccess = MutableLiveData<Boolean>()
    val dataLoading = MutableLiveData<Boolean>()
    val dataError = MutableLiveData<Boolean>()


    fun fetchMoviesList(query: String) {
        dataLoading.value = true

        disposable.add(
            dataService.getMoviesList(API_KEY, query, 1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieSearchResponse>() {
                    override fun onSuccess(t: MovieSearchResponse) {
                        movieSearchResponse.value = t
                        dataSuccess.value = true
                        dataLoading.value = false
                        dataError.value = false
                    }

                    override fun onError(e: Throwable) {
                        dataSuccess.value = false
                        dataLoading.value = false
                        dataError.value = true
                    }
                })
        )
    }
}