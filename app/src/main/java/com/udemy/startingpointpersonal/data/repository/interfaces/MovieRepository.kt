package com.udemy.startingpointpersonal.data.repository.interfaces

import com.udemy.startingpointpersonal.data.api.ApiResult
import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import com.udemy.startingpointpersonal.domain.model.Movie

interface MovieRepository {

    suspend fun getPopularMovies(region: String): List<Movie>

    suspend fun findById(movieId: Int): Movie

    suspend fun clearMovies()

    suspend fun saveMovies(list: List<MovieEntity>)

    suspend fun getAllMovies(): List<Movie>


}