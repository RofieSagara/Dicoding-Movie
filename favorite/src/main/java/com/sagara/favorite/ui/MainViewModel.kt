package com.sagara.favorite.ui

import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sagara.favorite.database.DatabaseContract
import com.sagara.favorite.model.Favorites

class MainViewModel(
    private val contentResolver: ContentResolver
): ViewModel() {

    val favorite: LiveData<List<Favorites>> = liveData {
        val cursor = contentResolver.query(
            DatabaseContract.FavoriteMoviesColumn.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        emit(Favorites.fromCursor(cursor))
        cursor?.close()
    }

}