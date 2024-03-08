package com.udemy.startingpointpersonal.data.repository

import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import com.udemy.startingpointpersonal.domain.model.Movie as DomainMovie
import com.udemy.startingpointpersonal.data.database.dao.MovieDao
import com.udemy.startingpointpersonal.data.database.entity.toDomainMovie
import com.udemy.startingpointpersonal.data.database.entity.toDomainMovies
import com.udemy.startingpointpersonal.data.repository.interfaces.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : LocalDataSource {

    override suspend fun isEmpty() = withContext(Dispatchers.IO) { movieDao.movieCount() == 0 }

    override suspend fun saveMovies(movies: List<MovieEntity>) = withContext(Dispatchers.IO) {
        //converts List to vararg
        movieDao.insert(*movies.toTypedArray())
    }

    override suspend fun findById(movieId: Int): DomainMovie =
        withContext(Dispatchers.IO) { movieDao.findById(movieId).toDomainMovie() }

    /*suspend fun getMovies(): List<DomainMovie> = withContext(Dispatchers.IO) {
        movieDao.getAll().map { it.toDomainMovie() }
    }*/

    override fun getMovies(): Flow<List<DomainMovie>> = movieDao.getAllFlow()
        .flowOn(Dispatchers.IO)
        .map { it.toDomainMovies() }

    override suspend fun clearMovies() = withContext(Dispatchers.IO){
        movieDao.deleteAll()
    }
}
