package com.sagara.dicodingmovie.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sagara.dicodingmovie.room.entitiy.Favorites
import com.sagara.dicodingmovie.service.MovieService

class FavoriteViewModel(
    private val service: MovieService
): ViewModel() {

    val favorite: LiveData<List<Favorites>>
        get() = liveData {
            emitSource(service.loadFavorite())
        }
}