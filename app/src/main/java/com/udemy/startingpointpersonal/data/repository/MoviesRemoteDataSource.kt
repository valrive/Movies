package com.udemy.startingpointpersonal.data.repository

import com.udemy.startingpointpersonal.data.api.ApiService
import com.udemy.startingpointpersonal.data.api.Movie
import javax.inject.Inject
import javax.inject.Named

class MoviesRemoteDataSource @Inject constructor(
    @Named("apiKey") private val apiKey: String,
    private val api: ApiService
){

    suspend fun getPopularMovies(): List<Movie> {
        return api.getPopulardMovies(apiKey).results
    }

}
