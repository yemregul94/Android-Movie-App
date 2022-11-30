package com.example.movieapp.data.retrofit

import com.example.movieapp.data.model.MovieDetails
import com.example.movieapp.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesDao {

    //http://www.omdbapi.com/?s=batman&apikey=1382bd8a

    @GET("?")
    suspend fun searchMovies(@Query("s") searchQuery: String, @Query("page") page: Int, @Query("apikey") apikey: String, @Query("type") type: String): MoviesResponse

    @GET("?")
    suspend fun searchMovies(@Query("s") searchQuery: String, @Query("page") page: Int, @Query("apikey") apikey: String): MoviesResponse

    @GET("?")
    suspend fun loadDetails(@Query("i") movieID: String, @Query("apikey") apikey: String): MovieDetails

}