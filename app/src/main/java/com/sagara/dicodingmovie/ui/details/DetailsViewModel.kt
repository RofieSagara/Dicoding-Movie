package com.sagara.dicodingmovie.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sagara.dicodingmovie.room.entitiy.Favorites
import com.sagara.dicodingmovie.room.entitiy.FavoritesTvShow
import com.sagara.dicodingmovie.service.MovieService
import com.sagara.dicodingmovie.service.response.Movie

class DetailsViewModel(private val service: MovieService): ViewModel() {
    private val _item: MutableLiveData<Movie> by lazy { MutableLiveData<Movie>() }
    private val _isFavorite: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    val item: LiveData<Movie> = _item
    val isFavorite: LiveData<Boolean> get() = _isFavorite
    private var type = ""

    suspend fun bind(item: Movie, type: String){
        this.type = type
        _item.postValue(item)
        if (type == "movie") {
            val favorite = service.findSingleFavorite(item.id)
            if (favorite != null) {
                _isFavorite.postValue(true)
            }else{
                _isFavorite.postValue(false)
            }
        }else if (type == "tv") {
            val favorite = service.findSingleFavoriteTvShows(item.id)
            if (favorite != null) {
                _isFavorite.postValue(true)
            }else{
                _isFavorite.postValue(false)
            }
        }
    }

    suspend fun like(){
        if (type == "movie") {
            service.insertFavorite(Favorites.fromMovie(_item.value!!))
            _isFavorite.postValue(true)
        }else if (type == "tv") {
            service.insertFavoriteTvShows(FavoritesTvShow.fromMovie(_item.value!!))
            _isFavorite.postValue(true)
        }
    }

    suspend fun unLike(){
        if (type == "movie") {
            service.deleteSingleFavorite(_item.value!!.id)
            _isFavorite.postValue(false)
        }else if (type == "tv") {
            service.deleteSingleFavoriteTvShows(_item.value!!.id)
            _isFavorite.postValue(false)
        }
    }
}