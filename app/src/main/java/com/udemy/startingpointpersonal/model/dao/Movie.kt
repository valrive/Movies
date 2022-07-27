package com.udemy.startingpointpersonal.model.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity//(tableName = "movie")
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val originalLanguage: String,
    val overview: String
)