package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.datasource.MoviesDataSource
import com.example.movieapp.data.repo.MoviesRepository
import com.example.movieapp.data.retrofit.ApiUtils
import com.example.movieapp.data.retrofit.MoviesDao
import com.example.movieapp.data.room.Database
import com.example.movieapp.data.room.FavoritesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(mds: MoviesDataSource) : MoviesRepository {
        return MoviesRepository(mds)
    }

    @Provides
    @Singleton
    fun provideMoviesDataSource(mdao: MoviesDao, fdao:FavoritesDao) : MoviesDataSource {
        return MoviesDataSource(mdao, fdao)
    }

    @Provides
    @Singleton
    fun provideMoviesDao(): MoviesDao {
        return ApiUtils.getMoviesDao()
    }

    @Provides
    @Singleton
    fun provideFavoritesDAO(@ApplicationContext context: Context) : FavoritesDao {
        val db = Room.databaseBuilder(context, Database::class.java, "favorites.db").createFromAsset("favorites.db").build()
        return db.getFavoritesDao()
    }
}