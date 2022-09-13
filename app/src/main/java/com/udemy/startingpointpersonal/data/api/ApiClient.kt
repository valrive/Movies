package com.udemy.startingpointpersonal.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiClient {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String): RemoteResult

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String): RemoteResult

    @GET("movie/popular")
    suspend fun getPopulardMovies(
        @Query("api_key") apiKey: String,
        @Query("region") countryCode: String
    ): RemoteResult

    @GET("movie/popular")
    fun getPopulardMoviesCall(
        @Query("api_key") apiKey: String,
        @Query("region") countryCode: String): Call<RemoteResult>

    companion object {

        const val DATE_FORMAT = "dd/MM/yyyy"
    }

}