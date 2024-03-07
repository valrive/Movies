package com.udemy.startingpointpersonal.data.repository

import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import com.udemy.startingpointpersonal.domain.model.Movie as DomainMovie
import com.udemy.startingpointpersonal.data.database.dao.MovieDao
import com.udemy.startingpointpersonal.data.repository.interfaces.MoviesLocalDataSource
import com.udemy.startingpointpersonal.data.toDomainMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : MoviesLocalDataSource {

    override suspend fun isEmpty() = withContext(Dispatchers.IO) { movieDao.movieCount() == 0 }

    override suspend fun saveMovies(movies: List<MovieEntity>) = withContext(Dispatchers.IO) {
        movieDao.insert(
            //converts List to vararg
            *movies.toTypedArray()
        )
    }

    override suspend fun findById(movieId: Int): DomainMovie =
        withContext(Dispatchers.IO) { movieDao.findById(movieId).toDomainMovie() }

    override suspend fun getMovies(): List<DomainMovie> = withContext(Dispatchers.IO) {
        movieDao.getAll().map { it.toDomainMovie() }
    }

    override suspend fun clearMovies() = withContext(Dispatchers.IO){
        movieDao.deleteAll()
    }
}
