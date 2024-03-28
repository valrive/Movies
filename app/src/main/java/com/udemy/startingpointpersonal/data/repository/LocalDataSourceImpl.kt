package com.udemy.startingpointpersonal.data.repository

import com.udemy.startingpointpersonal.domain.model.Movie as DomainMovie
import com.udemy.startingpointpersonal.data.database.dao.MovieDao
import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import com.udemy.startingpointpersonal.data.database.entity.toDomainMovie
import com.udemy.startingpointpersonal.data.database.entity.toDomainMovies
import com.udemy.startingpointpersonal.data.repository.interfaces.LocalDataSource
import com.udemy.startingpointpersonal.ui.view.popularMovs.ViewState
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

    override suspend fun size(): Int = withContext(Dispatchers.IO) { movieDao.movieCount() }

    override suspend fun saveMovies(movies: List<MovieEntity>) = withContext(Dispatchers.IO) {
        movieDao.insert(*movies.toTypedArray())//converts List to vararg
    }

    override suspend fun findById(movieId: Int): DomainMovie =
        withContext(Dispatchers.IO) { movieDao.findById(movieId).toDomainMovie() }

    /*suspend fun getMovies(): List<DomainMovie> = withContext(Dispatchers.IO) {
        movieDao.getAll().map { it.toDomainMovie() }
    }*/

    override fun getMovies(): Flow<ViewState<List<DomainMovie>>> = movieDao.getMoviesFlow()
        .flowOn(Dispatchers.IO)
        .map {
            ViewState.Success(it.toDomainMovies())
        }

    override suspend fun clearMovies() = withContext(Dispatchers.IO){
        movieDao.deleteAll()
    }
}
