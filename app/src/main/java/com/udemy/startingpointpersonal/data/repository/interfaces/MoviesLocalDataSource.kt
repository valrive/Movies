package com.udemy.startingpointpersonal.data.repository.interfaces

import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import com.udemy.startingpointpersonal.domain.model.Movie as DomainMovie

interface MoviesLocalDataSource {

    suspend fun isEmpty(): Boolean

    suspend fun insert(movies: List<MovieEntity>)

    suspend fun findById(movieId: Int): DomainMovie

    suspend fun getAll(): List<DomainMovie>

    suspend fun clearMovies()

}