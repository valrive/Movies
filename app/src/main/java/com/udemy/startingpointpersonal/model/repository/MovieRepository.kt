package com.udemy.startingpointpersonal.model.repository

import com.udemy.startingpointpersonal.model.api.ApiService
import com.udemy.startingpointpersonal.model.api.ApiResult
import com.udemy.startingpointpersonal.model.api.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class MovieRepository @Inject constructor(
    @Named("apiKey") private val apiKey: String,
    val api: ApiService
) {

    suspend fun getUpcomingMovies(): List<Movie> = withContext(Dispatchers.IO) {
        api.getUpcomingMovies(apiKey).results
    }

    suspend fun getTopRatedMovies(): List<Movie> = withContext(Dispatchers.IO) {
        api.getTopRatedMovies(apiKey).results
    }

    suspend fun getPopularMovies(): ApiResult<List<Movie>> = withContext(Dispatchers.IO) {
        ApiResult.Success(
            api.getPopulardMovies(apiKey).results
                    //responseToken = ApiResults.createForNonApiBody(api.requestToken(header))
                    //user = ApiResults.createForNonApiBody(api.login("Bearer ${responseToken!!.token}"))
        )
    }
}