package com.udemy.startingpointpersonal.data

import com.udemy.startingpointpersonal.data.api.MovieRemote
import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import com.udemy.startingpointpersonal.domain.model.Movie

fun MovieRemote.toDomainMovie(): Movie = Movie(
    id,
    title,
    "https://image.tmdb.org/t/p/w185/$posterPath",
    "https://image.tmdb.org/t/p/w780/$backdropPath",
    voteAverage,
    voteCount,
    releaseDate,
    originalLanguage,
    //"$overview $overview $overview $overview $overview $overview $overview $overview $overview $overview $overview $overview $overview $overview $overview "
    overview
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