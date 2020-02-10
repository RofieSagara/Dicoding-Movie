package com.sagara.dicodingmovie.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sagara.dicodingmovie.service.MovieService
import com.sagara.dicodingmovie.service.response.Movie
import com.sagara.dicodingmovie.utils.livedata.Event

class SearchViewModel(
    private val service: MovieService
): ViewModel() {
    private val _search by lazy { MutableLiveData<List<Movie>>() }
    private val _state by lazy { MutableLiveData<Event<SearchState>>() }

    val search: LiveData<List<Movie>> = _search
    val state: LiveData<Event<SearchState>> = _state

    var searchQuery = ""

    suspend fun search(keyword: String){
        try{
            service.search(keyword)
                .also {
                    _search.postValue(it)
                }
        }catch (ex: Exception){
            ex.printStackTrace()
            _state.postValue(Event(SearchState.Error(ex.message)))
        }
    }
}