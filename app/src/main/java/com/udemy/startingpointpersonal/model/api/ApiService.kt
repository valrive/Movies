package com.udemy.startingpointpersonal.model.api

import com.udemy.startingpointpersonal.model.pojos.RemoteResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String): RemoteResult

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String): RemoteResult

    @GET("movie/popular")
    suspend fun getPopulardMovies(@Query("api_key") apiKey: String): RemoteResult

    @GET("movie/popular")
    suspend fun getPopulardMoviesNew(@Query("api_key") apiKey: String): Call<RemoteResult>

    companion object {

        const val DATE_FORMAT = "dd/MM/yyyy"
    }

}