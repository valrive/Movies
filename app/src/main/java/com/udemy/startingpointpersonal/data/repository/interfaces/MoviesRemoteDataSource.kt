package com.udemy.startingpointpersonal.data.repository.interfaces

import com.udemy.startingpointpersonal.data.api.Movie

interface MoviesRemoteDataSource {

    suspend fun getPopularMovies(countryCode: String): List<Movie>
    fun getPopularMoviesCall(countryCode: String): List<Movie>
}