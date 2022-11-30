package com.example.movieapp.data.repo

import com.example.movieapp.data.datasource.MoviesDataSource
import com.example.movieapp.data.model.Favorites
import com.example.movieapp.data.model.MovieDetails
import com.example.movieapp.data.model.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(var mds: MoviesDataSource) {

    suspend fun searchMovies(searchQuery: String, page: Int) : MoviesResponse = mds.searchMovies(searchQuery, page)

    suspend fun loadDetails(movieID: String) : MovieDetails = mds.loadDetails(movieID)

    suspend fun addToFavorites(movieID: String) = mds.addToFavorites(movieID)

    suspend fun removeFromFavorites(movieID: String) = mds.removeFromFavorites(movieID)

    suspend fun checkFavorites(movieID: String): Boolean = mds.checkFavorites(movieID)

    suspend fun returnFavorites(): List<Favorites> = mds.returnFavorites()

}