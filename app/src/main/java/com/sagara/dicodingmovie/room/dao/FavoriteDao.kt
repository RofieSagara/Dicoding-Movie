package com.sagara.dicodingmovie.room.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.sagara.dicodingmovie.room.entitiy.Favorites

@Dao
interface FavoriteDao: BaseDao<Favorites>{

    @Query("SELECT * FROM favorites")
    fun load(): LiveData<List<Favorites>>

    @Query("SELECT * FROM favorites")
    fun loadStatic(): List<Favorites>

    @Query("SELECT * FROM favorites WHERE id = :id")
    fun findSingle(id: Int): Favorites?

    @Query("DELETE FROM favorites WHERE id = :id")
    fun deleteSingle(id: Int)

    @Query("SELECT * FROM favorites")
    fun selectAll(): Cursor
}