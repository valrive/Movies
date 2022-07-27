package com.udemy.startingpointpersonal.data.repository

import com.udemy.startingpointpersonal.data.dao.Movie
import com.udemy.startingpointpersonal.data.dao.MovieDao
import com.udemy.startingpointpersonal.data.toDomainMovie
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor( private val movieDao: MovieDao ) {

    fun isEmpty() = movieDao.movieCount() == 0

    fun save(movies: List<Movie>) {
        movieDao.insert(
            //converts List to vararg
            *movies.toTypedArray()
        )
    }

    fun findById(movieId: Int) {
        movieDao.findById(movieId).toDomainMovie()
    }

    fun getAll() = movieDao.getAll().map { it.toDomainMovie() }
}
