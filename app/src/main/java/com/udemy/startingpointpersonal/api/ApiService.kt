package com.udemy.startingpointpersonal.api

import com.udemy.startingpointpersonal.pojos.MovieList
import com.udemy.startingpointpersonal.pojos.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ApiService {

    @GET("user/me/login")
    fun login(@Header("Authorization") token: String): Call<User>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String): MovieList

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String): MovieList

    @GET("movie/popular")
    suspend fun getPopulardMovies(@Query("api_key") apiKey: String): MovieList

    @GET("movie/popular")
    suspend fun getPopulardMoviesNew(@Query("api_key") apiKey: String): Call<MovieList>

    companion object {

        const val DATE_FORMAT = "dd/MM/yyyy"
    }

}