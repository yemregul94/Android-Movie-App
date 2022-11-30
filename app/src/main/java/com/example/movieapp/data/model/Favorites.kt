package com.example.movieapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "favorites")
data class Favorites(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "favID") @NotNull var favID:Int,
                     @ColumnInfo(name = "movieID") @NotNull var movieID:String) : Serializable
