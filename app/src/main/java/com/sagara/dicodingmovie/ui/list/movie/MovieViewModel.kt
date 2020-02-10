package com.sagara.dicodingmovie.ui.list.movie

import com.sagara.dicodingmovie.service.MovieService
import com.sagara.dicodingmovie.service.response.ListMovies
import com.sagara.dicodingmovie.ui.list.ListItemViewModel

class MovieViewModel(
    service: MovieService
) : ListItemViewModel(service) {

    override suspend fun loadData(): ListMovies = service.discoverMovies()
}
