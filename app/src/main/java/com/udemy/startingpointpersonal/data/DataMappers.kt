package com.udemy.startingpointpersonal.data

import com.udemy.startingpointpersonal.data.api.Movie as ServerMovie
import com.udemy.startingpointpersonal.data.dao.Movie as DbMovie
import com.udemy.startingpointpersonal.data.pojos.Movie as DomainMovie

fun ServerMovie.toDbMovie(): DbMovie = DbMovie(
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