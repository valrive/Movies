package com.udemy.startingpointpersonal.model.repository

import com.udemy.startingpointpersonal.model.api.ApiService
import com.udemy.startingpointpersonal.model.api.Movie
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
