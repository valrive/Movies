package com.udemy.startingpointpersonal.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udemy.startingpointpersonal.domain.model.Movie

/**
 * Debemos agregar la anotación @ColumnInfo porque cuando se agregue ProGuard puede dar
 * problemas el renombramiento de variables
 */
@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "iddb") val idDB: Int = 0,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String? = "",
    @ColumnInfo(name = "posterPath") val posterPath: String,
    @ColumnInfo(name = "backdropPath") val backdropPath: String,
    @ColumnInfo(name = "voteAverage") val voteAverage: Double,
    @ColumnInfo(name = "voteCount") val voteCount: Int,
    @ColumnInfo(name = "releaseDate") val releaseDate: String,
    @ColumnInfo(name = "originalLanguage") val originalLanguage: String,
    @ColumnInfo(name = "overview") val overview: String
)

fun MovieEntity.toDomainMovie(): Movie = Movie(
    id,
    title,
    posterPath,
    backdropPath,
    voteAverage,
    voteCount,
    releaseDate,
    originalLanguage,
    overview
)

fun List<MovieEntity>.toDomainMovies(): List<Movie> = map { it.toDomainMovie() }