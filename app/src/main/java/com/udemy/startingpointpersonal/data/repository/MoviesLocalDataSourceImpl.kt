package com.udemy.startingpointpersonal.data.repository

import com.udemy.startingpointpersonal.data.dao.Movie
import com.udemy.startingpointpersonal.data.pojos.Movie as DomainMovie
import com.udemy.startingpointpersonal.data.dao.MovieDao
import com.udemy.startingpointpersonal.data.repository.interfaces.MoviesLocalDataSource
import com.udemy.startingpointpersonal.data.toDomainMovie
import javax.inject.Inject

class MoviesLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
    ): MoviesLocalDataSource {

    override fun isEmpty() = movieDao.movieCount() == 0

    override fun save(movies: List<Movie>) {
        movieDao.insert(
            //converts List to vararg
            *movies.toTypedArray()
        )
    }

    override fun findById(movieId: Int): DomainMovie = movieDao.findById(movieId).toDomainMovie()

    override fun getAll(): List<DomainMovie> = movieDao.getAll().map { it.toDomainMovie() }
}
