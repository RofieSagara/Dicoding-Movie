package com.sagara.dicodingmovie.service.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class ListMovies(
    var page: Int = 0,
    @SerializedName("total_results") var totalResult: Int = 0,
    @SerializedName("total_pages") var totalPage: Int = 0,
    var results: List<Movie> = listOf()
): Parcelable