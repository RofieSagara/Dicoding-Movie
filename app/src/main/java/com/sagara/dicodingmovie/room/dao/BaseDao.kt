package com.sagara.dicodingmovie.room.dao

import androidx.room.*

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg data: T)

    @Delete
    fun delete(vararg data: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<T>)

    @Query("DELETE FROM favorites WHERE 1")
    fun clear()
}