package com.ibrahimdemir.moviesapp.network

import com.ibrahimdemir.moviesapp.model.MovieDetailResponse
import com.ibrahimdemir.moviesapp.model.MovieSearchResponse
import com.ibrahimdemir.moviesapp.util.ApiConstants.BASE_URL
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitFactory {

    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
        .build()

    private val makeRetrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(RetrofitService::class.java)

    fun getMoviesList(api_key: String?, query: String?, page: Int?): Single<MovieSearchResponse> {
        return makeRetrofitService.getMoviesList(api_key, query, page)
    }

    fun getMovieDetail(id: Int?,api_key: String?): Single<MovieDetailResponse> {
        return makeRetrofitService.getMovieDetail(id, api_key)
    }
}