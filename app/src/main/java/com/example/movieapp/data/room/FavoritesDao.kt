package com.example.movieapp.data.room

import androidx.room.*
import com.example.movieapp.data.model.Favorites

@Dao
interface FavoritesDao {

    @Insert
    suspend fun addToFavorites(movie: Favorites)

    @Query("DELETE FROM favorites WHERE movieID like :movieID")
    suspend fun removeFromFavorites(movieID: String)

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE movieID like :movieID)")
    suspend fun checkFavorites(movieID: String): Boolean

    @Query("SELECT * FROM favorites")
    suspend fun returnFavorites(): List<Favorites>
}