package com.sagara.dicodingmovie.ui.list.tvseries

import com.sagara.dicodingmovie.service.MovieService
import com.sagara.dicodingmovie.service.response.ListMovies
import com.sagara.dicodingmovie.ui.list.ListItemViewModel

class TvSeriesViewModel(
    service: MovieService
) : ListItemViewModel(service) {

    override suspend fun loadData(): ListMovies = service.discoverTv()
}
