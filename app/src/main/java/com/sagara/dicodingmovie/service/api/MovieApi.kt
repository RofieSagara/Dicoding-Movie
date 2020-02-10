package com.sagara.dicodingmovie.service.api

import com.sagara.dicodingmovie.service.response.ListMovies
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/3/discover/movie")
    suspend fun discoverMovie(
        @Query("language") language: String,
        @Query("api_key") key: String,
        @Query("sort_by") sortBy: String
    ): ListMovies

    @GET("/3/discover/tv")
    suspend fun discoverTv(
        @Query("language") language: String,
        @Query("api_key") key: String,
        @Query("sort_by") sortBy: String
    ): ListMovies

    @GET("/3/search/movie")
    suspend fun searchMovies(
        @Query("language") language: String,
        @Query("api_key") key: String,
        @Query("query") keyword: String
    ): ListMovies

    @GET("/3/search/tv")
    suspend fun searchTvs(
        @Query("language") language: String,
        @Query("api_key") key: String,
        @Query("query") keyword: String
    ): ListMovies

    @GET("/3/discover/movie")
    suspend fun release(
        @Query("api_key") key: String,
        @Query("primary_release_date.gte") timeGte: String,
        @Query("primary_release_date.lte") timeLte: String
    ): ListMovies
}