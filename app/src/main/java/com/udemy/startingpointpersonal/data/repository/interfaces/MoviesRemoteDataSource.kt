package com.udemy.startingpointpersonal.data.repository.interfaces

import com.udemy.startingpointpersonal.data.api.MovieRemote

interface MoviesRemoteDataSource {

    suspend fun getPopularMovies(countryCode: String): List<MovieRemote>

    suspend fun getPopularMoviesCall(countryCode: String): List<MovieRemote>
}