package com.udemy.startingpointpersonal.data.api

import com.google.gson.annotations.SerializedName
import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import com.udemy.startingpointpersonal.domain.model.Movie

data class RemoteResult(
    val page: Int,
    val results: List<MovieRemote>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class MovieRemote(
    val id: Int,
    val adult: Boolean = false,
    @SerializedName("backdrop_path") val backdropPath: String = "",
    @SerializedName("genre_ids") val genreIds: List<Int> = emptyList(),
    @SerializedName("original_title") val originalTitle: String = "",
    @SerializedName("original_language") val originalLanguage: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    @SerializedName("poster_path") val posterPath: String = "",
    @SerializedName("release_date") val releaseDate: String = "",
    val title: String = "",
    val video: Boolean = false,
    @SerializedName("vote_average") val voteAverage: Double = 0.0,
    @SerializedName("vote_count") val voteCount: Int = 0
)

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

fun List<MovieRemote>.toDomainMovies(): List<Movie> = map { it.toDomainMovie() }

fun MovieRemote.toEntityMovie(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    posterPath = "https://image.tmdb.org/t/p/w185/$posterPath",
    backdropPath ="https://image.tmdb.org/t/p/w780/$backdropPath",
    voteAverage = voteAverage,
    voteCount = voteCount,
    releaseDate = releaseDate,
    originalLanguage = originalLanguage,
    //"$overview $overview $overview $overview $overview $overview $overview $overview $overview $overview $overview $overview $overview $overview $overview "
    overview = overview
)

fun List<MovieRemote>.toEntityMovies(): List<MovieEntity> = map { it.toEntityMovie() }
