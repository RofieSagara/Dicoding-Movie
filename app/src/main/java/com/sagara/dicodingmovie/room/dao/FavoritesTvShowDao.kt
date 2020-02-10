package com.sagara.dicodingmovie.room.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.sagara.dicodingmovie.room.entitiy.FavoritesTvShow

@Dao
interface FavoritesTvShowDao: BaseDao<FavoritesTvShow> {

    @Query("SELECT * FROM favoritesTvShow")
    fun load(): LiveData<List<FavoritesTvShow>>

    @Query("SELECT * FROM favoritesTvShow")
    fun loadStatic(): List<FavoritesTvShow>

    @Query("SELECT * FROM favoritesTvShow WHERE id = :id")
    fun findSingle(id: Int): FavoritesTvShow?

    @Query("DELETE FROM favoritesTvShow WHERE id = :id")
    fun deleteSingle(id: Int)

    @Query("SELECT * FROM favoritesTvShow")
    fun selectAll(): Cursor
}