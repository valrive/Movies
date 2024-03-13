package com.udemy.startingpointpersonal.data.repository

import com.udemy.startingpointpersonal.data.api.ApiClient
import com.udemy.startingpointpersonal.data.api.MovieRemote
import com.udemy.startingpointpersonal.data.api.wrapper.ApiWrapper
import com.udemy.startingpointpersonal.data.repository.interfaces.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class RemoteDataSourceImpl @Inject constructor(
    @Named("apiKey") private val apiKey: String,
    private val api: ApiClient
) : RemoteDataSource {

    override suspend fun getPopularMovies(countryCode: String): List<MovieRemote> =
        withContext( Dispatchers.IO) {
            api.getPopulardMovies(apiKey, countryCode).results
    }

    override suspend fun getPopularMoviesCall(countryCode: String, page: Int): List<MovieRemote> =
        withContext(Dispatchers.IO) {
            val body =
                //ApiWrapper.createForRequiredBody(api.getPopulardMoviesCall(apiKey, countryCode, page))
                ApiWrapper.createForRequiredBody(api.getPopulardMoviesCall(apiKey, page))
            body.results
        }

}
