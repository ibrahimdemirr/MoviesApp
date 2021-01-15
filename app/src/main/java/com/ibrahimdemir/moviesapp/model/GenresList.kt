package com.ibrahimdemir.moviesapp.model

import com.google.gson.annotations.SerializedName

data class GenresList(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?
)