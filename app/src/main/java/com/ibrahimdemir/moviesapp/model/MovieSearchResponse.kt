package com.ibrahimdemir.moviesapp.model

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val results: List<MovieList>?,
    @SerializedName("total_pages") val total_pages: Int?,
    @SerializedName("total_results") val total_results: Int?
)
