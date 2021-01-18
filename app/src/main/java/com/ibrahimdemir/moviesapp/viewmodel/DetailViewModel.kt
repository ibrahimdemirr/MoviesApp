package com.ibrahimdemir.moviesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ibrahimdemir.moviesapp.base.BaseViewModel
import com.ibrahimdemir.moviesapp.model.MovieDetailResponse
import com.ibrahimdemir.moviesapp.network.RetrofitFactory
import com.ibrahimdemir.moviesapp.util.ApiConstants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DetailViewModel : BaseViewModel() {

    private val dataService = RetrofitFactory()

    val movieDetailResponse = MutableLiveData<MovieDetailResponse>()
    val dataSuccess = MutableLiveData<Boolean>()
    val dataLoading = MutableLiveData<Boolean>()
    val dataError = MutableLiveData<Boolean>()

    fun fetchMovieDetail(id: Int?) {
        dataLoading.value = true

        compositeDisposable.add(
            dataService.getMovieDetail(id, ApiConstants.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieDetailResponse>() {
                    override fun onSuccess(t: MovieDetailResponse) {
                        movieDetailResponse.value = t
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