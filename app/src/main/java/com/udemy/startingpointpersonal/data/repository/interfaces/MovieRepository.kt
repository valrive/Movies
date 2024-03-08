package com.udemy.startingpointpersonal.data.repository.interfaces

import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import com.udemy.startingpointpersonal.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(region: String): Flow<List<Movie>>

    suspend fun findById(movieId: Int): Movie

    suspend fun clearMovies()

    suspend fun insertMovies(list: List<MovieEntity>)


}