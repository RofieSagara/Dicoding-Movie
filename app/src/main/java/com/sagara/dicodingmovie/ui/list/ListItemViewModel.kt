package com.sagara.dicodingmovie.ui.list

import androidx.lifecycle.*
import com.sagara.dicodingmovie.service.MovieService
import com.sagara.dicodingmovie.service.response.ListMovies
import com.sagara.dicodingmovie.utils.livedata.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class ListItemViewModel(
    protected val service: MovieService
) : ViewModel() {
    private val _state = MutableLiveData<Event<ListItemState>>()
    private val _item = MutableLiveData<ListMovies>()

    val item: LiveData<ListMovies> get() = _item
    val state: LiveData<Event<ListItemState>> get() = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(Event(ListItemState.ShowLoading))
            try{
                _item.postValue(loadData())
            }catch (ex: Exception){
                _state.postValue(Event(ListItemState.Error(ex.message ?: "")))
            }finally {
                _state.postValue(Event(ListItemState.HideLoading))
            }
        }
    }

    abstract suspend fun loadData(): ListMovies

    suspend fun refresh(){
        withContext(Dispatchers.IO) {
            loadData().also {
                try{
                    _item.postValue(it)
                }catch (ex: Exception){
                    _state.postValue(Event(ListItemState.Error(ex.message ?: "")))
                }finally {
                    _state.postValue(Event(ListItemState.HideLoading))
                }
            }
        }
    }
}
