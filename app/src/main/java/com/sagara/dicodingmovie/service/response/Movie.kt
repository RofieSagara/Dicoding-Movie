package com.sagara.dicodingmovie.service.response

import android.os.Parcelable
import com.google.gson.annotations.JsonAdapter
import com.sagara.dicodingmovie.gson.MovieDeserializer
import com.sagara.dicodingmovie.room.entitiy.Favorites
import com.sagara.dicodingmovie.room.entitiy.FavoritesTvShow
import kotlinx.android.parcel.Parcelize

@JsonAdapter(MovieDeserializer::class)
@Parcelize
data class Movie(
    var id: Int,
    var popularity: Double = 0.0,
    var voteCount: Int = 0,
    var poster: String = "",
    var title: String = "",
    var voteAverage: Double = 0.0,
    var overview: String = "",
    var releaseDate: String = ""
): Parcelable {

    companion object {
        fun fromFavorite(item: Favorites): Movie = Movie(
            item.id,
            item.popularity,
            item.voteCount,
            item.poster,
            item.title,
            item.voteAverage,
            item.overview,
            item.releaseDate
        )

        fun fromFavorite(item: FavoritesTvShow): Movie = Movie(
            item.id,
            item.popularity,
            item.voteCount,
            item.poster,
            item.title,
            item.voteAverage,
            item.overview,
            item.releaseDate
        )
    }
}