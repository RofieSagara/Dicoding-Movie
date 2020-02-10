package com.sagara.dicodingmovie.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sagara.dicodingmovie.room.entitiy.Favorites
import com.sagara.dicodingmovie.room.entitiy.FavoritesTvShow

@Database(entities = [Favorites::class, FavoritesTvShow::class], version = 1)
abstract class AppDatabase: RoomDatabase(), IAppDatabase {

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "aqua-chat.db"
                    )
                        .build()
                }
            }
            return INSTANCE ?: throw Exception("AppDatabase Instance Failed")
        }
    }
}