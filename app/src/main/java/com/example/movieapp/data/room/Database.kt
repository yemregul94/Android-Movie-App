package com.example.movieapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.model.Favorites

@Database(entities = [Favorites::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun getFavoritesDao() : FavoritesDao
}