package com.udemy.startingpointpersonal.data.repository

import com.udemy.startingpointpersonal.data.api.ApiService
import com.udemy.startingpointpersonal.data.api.Movie
import com.udemy.startingpointpersonal.data.repository.interfaces.MoviesRemoteDataSource
import javax.inject.Inject
import javax.inject.Named

class MoviesRemoteDataSourceImpl @Inject constructor(
    @Named("apiKey") private val apiKey: String,
    private val api: ApiService
): MoviesRemoteDataSource {

    override suspend fun getPopularMovies(): List<Movie> {
        return api.getPopulardMovies(apiKey).results
    }

}
