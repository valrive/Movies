package com.udemy.startingpointpersonal.data.repository

import com.udemy.startingpointpersonal.domain.model.Movie as DomainMovie
import com.udemy.startingpointpersonal.data.database.dao.MovieDao
import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import com.udemy.startingpointpersonal.data.database.entity.toDomainMovie
import com.udemy.startingpointpersonal.data.database.entity.toDomainMovies
import com.udemy.startingpointpersonal.data.repository.interfaces.LocalDataSource
import com.udemy.startingpointpersonal.ui.view.popularMovs.ViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : LocalDataSource {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun isEmpty() = withContext(dispatcher) { movieDao.movieCount() == 0 }

    override suspend fun size(): Int = withContext(dispatcher) { movieDao.movieCount() }

    override suspend fun saveMovies(movies: List<MovieEntity>) = withContext(dispatcher) {
        movieDao.insert(*movies.toTypedArray())//converts List to vararg
    }

    override suspend fun findById(movieId: Int): DomainMovie =
        withContext(dispatcher) { movieDao.findById(movieId).toDomainMovie() }

    /*suspend fun getMovies(): List<DomainMovie> = withContext(dispatcher) {
        movieDao.getAll().map { it.toDomainMovie() }
    }*/

    override fun getMovies(): Flow<ViewState<List<DomainMovie>>> = movieDao.getMoviesFlow()
        .flowOn(dispatcher)
        .map {
            ViewState.Success(it.toDomainMovies())
        }

    override suspend fun clearMovies() = withContext(dispatcher){
        movieDao.deleteAll()
    }
}
