package com.udemy.startingpointpersonal.data.repository.interfaces

import com.udemy.startingpointpersonal.data.dao.Movie
import com.udemy.startingpointpersonal.data.pojos.Movie as DomainMovie

interface MoviesLocalDataSource {

    fun isEmpty(): Boolean

    fun save(movies: List<Movie>)

    fun findById(movieId: Int): DomainMovie

    fun getAll(): List<DomainMovie>
}