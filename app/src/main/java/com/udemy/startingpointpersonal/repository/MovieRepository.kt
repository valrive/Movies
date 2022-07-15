package com.udemy.startingpointpersonal.repository

import com.udemy.startingpointpersonal.api.ApiResults
import com.udemy.startingpointpersonal.api.ApiService
import com.udemy.startingpointpersonal.core.ApiResult
import com.udemy.startingpointpersonal.pojos.MovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class MovieRepository @Inject constructor(
    @Named("apiKey") private val apiKey: String,
    val api: ApiService
){

    suspend fun getUpcomingMovies(): MovieList = withContext(Dispatchers.IO) {
        api.getUpcomingMovies(apiKey)
    }

    suspend fun getTopRatedMovies(): MovieList = withContext(Dispatchers.IO) {
        api.getTopRatedMovies(apiKey)
    }

    suspend fun getPopularMovies(): ApiResult<MovieList>  =
        withContext(Dispatchers.IO) {
            ApiResult.Success(api.getPopulardMovies(apiKey))
        }

    suspend fun getPopularMoviesResource(): ApiResult<MovieList>  =
        withContext(Dispatchers.IO) {
            ApiResults.createForResultNew(api.getPopulardMoviesNew(apiKey))
        }
}