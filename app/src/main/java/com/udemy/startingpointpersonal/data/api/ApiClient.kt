package com.udemy.startingpointpersonal.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiClient {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String): RemoteResult

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String): RemoteResult

    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun getPopulardMovies(
        @Query("api_key") apiKey: String,
        @Query("region") countryCode: String,
        @Query("page") page: Int
    ): RemoteResult

    //@GET("movie/popular")
    @GET("discover/movie?sort_by=popularity.desc")
    fun getPopulardMoviesCall(
        @Query("api_key") apiKey: String,
        @Query("region") countryCode: String,
        @Query("page") page: Int): Call<RemoteResult>

    companion object {

        const val DATE_FORMAT = "dd/MM/yyyy"
    }

}