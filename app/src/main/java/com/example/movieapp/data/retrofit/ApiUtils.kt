package com.example.movieapp.data.retrofit

import com.example.movieapp.util.BASE_URL

class ApiUtils {
    companion object{
        fun getMoviesDao(): MoviesDao{
            return RetrofitClient.getClient(BASE_URL).create(MoviesDao::class.java)
        }
    }
}