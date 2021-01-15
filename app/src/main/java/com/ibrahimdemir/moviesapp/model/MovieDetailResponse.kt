package com.ibrahimdemir.moviesapp.model

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("backdrop_path") val backdrop_path: String?,
    @SerializedName("poster_path") val poster_path: String?,
    @SerializedName("original_title") val original_title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("vote_average") val vote_average: Double?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("genres") val genres: List<GenresList>?
)