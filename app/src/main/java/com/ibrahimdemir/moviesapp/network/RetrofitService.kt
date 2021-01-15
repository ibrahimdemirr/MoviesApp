package com.ibrahimdemir.moviesapp.network

import com.ibrahimdemir.moviesapp.model.MovieDetailResponse
import com.ibrahimdemir.moviesapp.model.MovieSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("search/movie")
    fun getMoviesList(
        @Query("api_key") api_key: String?,
        @Query("query") query: String?,
        @Query("page") page: Int?
    ): Single<MovieSearchResponse>

    @GET("movie/{id}")
    fun getMovieDetail(
        @Path("id") id: Int?,
        @Query("api_key") api_key: String?
    ): Single<MovieDetailResponse>
}