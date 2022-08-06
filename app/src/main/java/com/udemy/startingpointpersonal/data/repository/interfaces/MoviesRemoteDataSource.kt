package com.udemy.startingpointpersonal.data.repository.interfaces

import com.udemy.startingpointpersonal.data.api.Movie

interface MoviesRemoteDataSource {

    suspend fun getPopularMovies(): List<Movie>
}