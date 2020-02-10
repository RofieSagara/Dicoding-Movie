package com.sagara.favorite.model

import android.database.Cursor
import android.os.Parcelable
import com.sagara.favorite.database.DatabaseContract.FavoriteMoviesColumn
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorites(
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
        fun fromCursor(cursor: Cursor?): List<Favorites>{
            val favoriteMovieList = arrayListOf<Favorites>()
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val movieId = cursor.getInt(
                        cursor.getColumnIndex(FavoriteMoviesColumn.MOVIE_ID)
                    )
                    val popularity = cursor.getDouble(
                        cursor.getColumnIndex(FavoriteMoviesColumn.POPULARITY)
                    )
                    val voteCount = cursor.getInt(
                        cursor.getColumnIndex(FavoriteMoviesColumn.VOTE_COUNT)
                    )
                    val poster = cursor.getString(
                        cursor.getColumnIndex(FavoriteMoviesColumn.POSTER)
                    )
                    val title = cursor.getString(
                        cursor.getColumnIndex(FavoriteMoviesColumn.TITLE)
                    )
                    val voteAverage = cursor.getDouble(
                        cursor.getColumnIndex(FavoriteMoviesColumn.VOTE_AVERANGE)
                    )
                    val overview = cursor.getString(
                        cursor.getColumnIndex(FavoriteMoviesColumn.OVERVIEW)
                    )
                    val releaseDate = cursor.getString(
                        cursor.getColumnIndex(FavoriteMoviesColumn.RELEASE_DATE)
                    )
                    favoriteMovieList.add(
                        Favorites(
                            movieId,
                            popularity,
                            voteCount,
                            poster,
                            title,
                            voteAverage,
                            overview,
                            releaseDate
                        )
                    )
                }
            }
            return favoriteMovieList
        }
    }
}