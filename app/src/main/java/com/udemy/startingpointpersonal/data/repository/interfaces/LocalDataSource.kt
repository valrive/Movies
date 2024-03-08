package com.udemy.startingpointpersonal.data.repository.interfaces

import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import com.udemy.startingpointpersonal.domain.model.Movie as DomainMovie

interface LocalDataSource {

    suspend fun isEmpty(): Boolean

    suspend fun saveMovies(movies: List<MovieEntity>)

    suspend fun findById(movieId: Int): DomainMovie

    //suspend fun getMovies(): List<DomainMovie>
    fun getMovies(): Flow<List<DomainMovie>>

    suspend fun clearMovies()

}