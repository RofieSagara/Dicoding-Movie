package com.sagara.dicodingmovie.ui.favorite.favoritelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sagara.dicodingmovie.room.entitiy.Favorites
import com.sagara.dicodingmovie.service.MovieService

class FavoriteListViewModel(
    private val service: MovieService
) : ViewModel() {

    val movies: LiveData<List<Favorites>>
        get() = liveData {
            emitSource(service.loadFavorite())
        }
}
