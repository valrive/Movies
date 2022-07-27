package com.udemy.startingpointpersonal.model

import com.udemy.startingpointpersonal.model.api.Movie as ServerMovie
import com.udemy.startingpointpersonal.model.dao.Movie as DbMovie
import com.udemy.startingpointpersonal.model.pojos.Movie as DomainMovie

fun ServerMovie.toDbMovie(): DbMovie = DbMovie(
    id,
    title,
    "https://image.tmdb.org/t/p/w500/$posterPath",
    "https://image.tmdb.org/t/p/w500/$backdropPath",
    voteAverage,
    voteCount,
    releaseDate,
    originalLanguage,
    overview
)

fun DbMovie.toDomainMovie(): DomainMovie = DomainMovie(
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