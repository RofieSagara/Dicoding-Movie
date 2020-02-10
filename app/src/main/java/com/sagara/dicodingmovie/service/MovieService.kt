package com.sagara.dicodingmovie.service

import androidx.lifecycle.LiveData
import com.sagara.dicodingmovie.room.IAppDatabase
import com.sagara.dicodingmovie.room.entitiy.Favorites
import com.sagara.dicodingmovie.room.entitiy.FavoritesTvShow
import com.sagara.dicodingmovie.service.api.MovieApi
import com.sagara.dicodingmovie.service.response.ListMovies
import com.sagara.dicodingmovie.service.response.Movie
import org.joda.time.DateTime

class MovieService(private val movieApi: MovieApi, private val appDatabase: IAppDatabase) {
    private val defaultLanguage = "en-Us"
    private val key = "9d470632e32c726c46cf17dded7895fd"
    private val defaultOrder = "popularity.desc"

    suspend fun discoverMovies(): ListMovies =
        movieApi.discoverMovie(defaultLanguage, key, defaultOrder)

    suspend fun discoverTv(): ListMovies =
        movieApi.discoverTv(defaultLanguage, key, defaultOrder)

    suspend fun loadFavorite(): LiveData<List<Favorites>> =
        appDatabase.favoriteDao().load()

    suspend fun loadFavoriteTvShows(): LiveData<List<FavoritesTvShow>> =
        appDatabase.favoriteTvShowDao().load()

    suspend fun findSingleFavorite(id: Int): Favorites? =
        appDatabase.favoriteDao().findSingle(id)

    suspend fun findSingleFavoriteTvShows(id: Int): FavoritesTvShow? =
        appDatabase.favoriteTvShowDao().findSingle(id)

    suspend fun deleteSingleFavorite(id: Int) =
        appDatabase.favoriteDao().deleteSingle(id)

    suspend fun deleteSingleFavoriteTvShows(id: Int) =
        appDatabase.favoriteTvShowDao().deleteSingle(id)

    suspend fun insertFavorite(item: Favorites) =
        appDatabase.favoriteDao().insert(item)

    suspend fun insertFavoriteTvShows(item: FavoritesTvShow) =
        appDatabase.favoriteTvShowDao().insert(item)

    suspend fun search(keyword: String): List<Movie> {
        val movie = movieApi.searchMovies(defaultLanguage, key, keyword)
        val tv = movieApi.searchTvs(defaultLanguage, key, keyword)
        val result = mutableListOf<Movie>()
        result.addAll(movie.results)
        result.addAll(tv.results)
        return result
    }

    suspend fun release(dateTime: DateTime): ListMovies =
        movieApi.release(
            key,
            dateTime.toString("yyyy-MM-dd"),
            dateTime.toString("yyyy-MM-dd")
        )
}