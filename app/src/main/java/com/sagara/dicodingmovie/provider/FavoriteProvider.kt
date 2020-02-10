package com.sagara.dicodingmovie.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.sagara.dicodingmovie.provider.DatabaseContract.Companion.AUTHORITY
import com.sagara.dicodingmovie.provider.DatabaseContract.FavoriteMoviesColumn.Companion.TABLE_FAVORITE_MOVIE
import com.sagara.dicodingmovie.provider.DatabaseContract.FavoriteTvSeriesColumn.Companion.TABLE_FAVORITE_TV_SERIES
import com.sagara.dicodingmovie.room.AppDatabase


class FavoriteProvider: ContentProvider() {

    companion object {
        private const val FAVORITE_MOVIE = 1

        private const val FAVORITE_TV_SERIES = 11

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_MOVIE, FAVORITE_MOVIE)
            //sUriMatcher.addURI(AUTHORITY, "$TABLE_FAVORITE_MOVIE/#", FAVORITE_MOVIE_ID)

            sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_TV_SERIES, FAVORITE_TV_SERIES)
            //sUriMatcher.addURI(AUTHORITY, "$TABLE_FAVORITE_TV_SERIES/#", FAVORITE_TV_SERIES_ID)
        }
    }

    //This Favorite Provider only read cant insert, update, delete
    //So we put 0 row update on delete, update
    //and null on insert
    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int = 0

    override fun getType(p0: Uri): String? = null

    override fun insert(p0: Uri, p1: ContentValues?): Uri? = null

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int = 0

    //We use ROOM so no need create. just return true
    override fun onCreate(): Boolean = true

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        return when(sUriMatcher.match(p0)){
            FAVORITE_MOVIE -> {
                val context = context ?: return null
                val favoriteDao = AppDatabase.getInstance(context).favoriteDao()
                val cursor = favoriteDao.selectAll()
                cursor.setNotificationUri(context.contentResolver, p0)
                cursor
            }
            FAVORITE_TV_SERIES -> {
                val context = context ?: return null
                val favoriteDao = AppDatabase.getInstance(context).favoriteTvShowDao()
                val cursor = favoriteDao.selectAll()
                cursor.setNotificationUri(context.contentResolver, p0)
                cursor
            }
            else -> return null
        }
    }
}