package com.udemy.startingpointpersonal.data.repository

import com.udemy.startingpointpersonal.data.api.ApiService
import com.udemy.startingpointpersonal.data.api.Movie
import com.udemy.startingpointpersonal.data.api.wrapper.ApiWrapper
import com.udemy.startingpointpersonal.data.repository.interfaces.MoviesRemoteDataSource
import javax.inject.Inject
import javax.inject.Named

class MoviesRemoteDataSourceImpl @Inject constructor(
    @Named("apiKey") private val apiKey: String,
    private val api: ApiService
): MoviesRemoteDataSource {

    override suspend fun getPopularMovies(countryCode: String): List<Movie> {
        return api.getPopulardMovies(apiKey, countryCode).results
    }

    override suspend fun getPopularMoviesCall(countryCode: String): List<Movie> {
        val body = ApiWrapper.createForRequiredBody(api.getPopulardMoviesCall(apiKey, countryCode))
        return body.results
    }

}
