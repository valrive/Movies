package com.udemy.startingpointpersonal.domain.model

import android.os.Parcelable
import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int = 0,
    val title: String? = "",
    val posterPath: String? = "",
    val backdropPath: String? = "",
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val releaseDate: String? = "",
    val originalLanguage: String? = "",
    val overview: String? = ""
    ): Parcelable

fun Movie.toEntityMovie(): MovieEntity = MovieEntity(
    id,
    title,
    "https://image.tmdb.org/t/p/w185/$posterPath",
    "https://image.tmdb.org/t/p/w780/$backdropPath",
    voteAverage,
    voteCount,
    releaseDate.orEmpty(),
    originalLanguage.orEmpty(),
    //"$overview $overview $overview $overview $overview $overview $overview $overview $overview $overview $overview $overview $overview $overview $overview "
    overview.orEmpty()
)

fun List<Movie>.toEntityMovies(): List<MovieEntity> = map { it.toEntityMovie() }

/**
 * Debemos tener 3 modelos de datos para ser lo más independientes posibles del api:
 *
 * En este caso tenemos :
 * Movie para la respuesta del server
 * Movie para guardado en db
 * Movie para usarlo en el dominio de la app
 */