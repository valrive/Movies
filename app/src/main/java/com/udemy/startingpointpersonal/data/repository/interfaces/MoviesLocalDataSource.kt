package com.udemy.startingpointpersonal.data.repository.interfaces

import com.udemy.startingpointpersonal.data.dao.Movie
import com.udemy.startingpointpersonal.data.pojos.Movie as DomainMovie

interface MoviesLocalDataSource {

    suspend fun isEmpty(): Boolean

    suspend fun save(movies: List<Movie>)

    suspend fun findById(movieId: Int): DomainMovie

    suspend fun getAll(): List<DomainMovie>
}