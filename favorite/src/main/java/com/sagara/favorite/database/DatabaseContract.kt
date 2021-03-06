package com.sagara.favorite.database

import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class DatabaseContract {

    companion object {
        const val AUTHORITY = "com.sagara.dicodingmovie"
        private const val SCHEME = "content"

        fun getColumnString(cursor: Cursor, columnName: String): String? {
            return cursor.getString(cursor.getColumnIndex(columnName))
        }

        fun getColumnInt(cursor: Cursor, columnName: String): Int {
            return cursor.getInt(cursor.getColumnIndex(columnName))
        }

        fun getColumnFloat(cursor: Cursor, columnName: String): Float {
            return cursor.getFloat(cursor.getColumnIndex(columnName))
        }
    }

    class FavoriteMoviesColumn : BaseColumns {
        companion object {
            const val TABLE_FAVORITE_MOVIE = "favorites"
            const val MOVIE_ID = "id"
            const val POPULARITY = "popularity"
            const val VOTE_COUNT = "voteCount"
            const val POSTER = "poster"
            const val TITLE = "title"
            const val VOTE_AVERANGE = "voteAverage"
            const val OVERVIEW = "overview"
            const val RELEASE_DATE = "releaseDate"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE_MOVIE)
                .build()
        }
    }

    class FavoriteTvSeriesColumn : BaseColumns {
        companion object {
            const val TABLE_FAVORITE_TV_SERIES = "favorite_tv_series"
            const val MOVIE_ID = "id"
            const val POPULARITY = "popularity"
            const val VOTE_COUNT = "voteCount"
            const val POSTER = "poster"
            const val TITLE = "title"
            const val VOTE_AVERANGE = "voteAverage"
            const val OVERVIEW = "overview"
            const val RELEASE_DATE = "releaseDate"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE_TV_SERIES)
                .build()
        }
    }
}