package com.sagara.dicodingmovie.room.entitiy

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sagara.dicodingmovie.service.response.Movie
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorites")
@Parcelize
data class Favorites(
    @PrimaryKey var id: Int,
    var popularity: Double = 0.0,
    var voteCount: Int = 0,
    var poster: String = "",
    var title: String = "",
    var voteAverage: Double = 0.0,
    var overview: String = "",
    var releaseDate: String = ""
): Parcelable{
    companion object {
        fun fromMovie(item: Movie): Favorites = Favorites(
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