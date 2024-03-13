package com.udemy.startingpointpersonal.data.repository.interfaces

import com.udemy.startingpointpersonal.data.api.MovieRemote

interface RemoteDataSource {

    suspend fun getPopularMovies(countryCode: String): List<MovieRemote>

    suspend fun getPopularMoviesCall(countryCode: String, page: Int): List<MovieRemote>
}