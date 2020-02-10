package com.sagara.dicodingmovie.room

import com.sagara.dicodingmovie.room.dao.FavoriteDao
import com.sagara.dicodingmovie.room.dao.FavoritesTvShowDao

interface IAppDatabase {

    fun favoriteDao(): FavoriteDao
    fun favoriteTvShowDao(): FavoritesTvShowDao
}