package com.sagara.dicodingmovie.ui.favorite.favoritetvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sagara.dicodingmovie.room.entitiy.FavoritesTvShow
import com.sagara.dicodingmovie.service.MovieService

class FavoriteTvShowsViewModel(
    private val service: MovieService
) : ViewModel() {

    val tvShows: LiveData<List<FavoritesTvShow>>
        get() = liveData {
            emitSource(service.loadFavoriteTvShows())
        }
}
