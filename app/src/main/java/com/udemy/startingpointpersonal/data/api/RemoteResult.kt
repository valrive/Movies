package com.udemy.startingpointpersonal.data.api

import com.google.gson.annotations.SerializedName
import com.udemy.startingpointpersonal.domain.model.Movie

data class RemoteResult(
    val page: Int,
    val results: List<MovieRemote>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class MovieRemote(
    val id: Int,
    val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("original_language") val originalLanguage: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
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
