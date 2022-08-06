package com.udemy.startingpointpersonal.data.repository.interfaces

import com.udemy.startingpointpersonal.data.api.ApiResult
import com.udemy.startingpointpersonal.data.pojos.Movie

interface MovieRepository {

    suspend fun getPopularMovies(): ApiResult<List<Movie>>

    suspend fun findById(movieId: Int): Movie
}