package com.example.movieapp.data.datasource


import com.example.movieapp.data.model.Favorites
import com.example.movieapp.data.model.MovieDetails
import com.example.movieapp.data.model.MoviesResponse
import com.example.movieapp.data.retrofit.MoviesDao
import com.example.movieapp.data.room.FavoritesDao
import com.example.movieapp.util.APIKEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesDataSource(var mdao: MoviesDao, var fdao: FavoritesDao) {

    suspend fun searchMovies(searchQuery: String, page: Int) : MoviesResponse =
        withContext(Dispatchers.IO){
            mdao.searchMovies(searchQuery, page, APIKEY, "movie")
    }

    suspend fun loadDetails(movieID: String) : MovieDetails =
        withContext(Dispatchers.IO){
            mdao.loadDetails(movieID, APIKEY)
        }

    suspend fun addToFavorites(movieID: String){
        val favMovie = Favorites(0, movieID)
        fdao.addToFavorites(favMovie)
    }

    suspend fun removeFromFavorites(movieID: String){
        val favMovie = Favorites(0, movieID)
        fdao.removeFromFavorites(movieID)
    }

    suspend fun checkFavorites(movieID: String): Boolean =
        withContext(Dispatchers.IO){
            fdao.checkFavorites(movieID)
        }

    suspend fun returnFavorites(): List<Favorites> =
        withContext(Dispatchers.IO){
            fdao.returnFavorites()
        }


}