package com.udemy.startingpointpersonal.model.repository

import com.udemy.startingpointpersonal.model.api.ApiService
import com.udemy.startingpointpersonal.model.api.ApiResult
import com.udemy.startingpointpersonal.model.pojos.Movie
import com.udemy.startingpointpersonal.model.dao.MovieDao
import com.udemy.startingpointpersonal.model.toDbMovie
import com.udemy.startingpointpersonal.model.toDomainMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class MovieRepository @Inject constructor(
    @Named("apiKey") private val apiKey: String,
    private val movieDao: MovieDao,
    private val api: ApiService
) {

    suspend fun getPopularMovies(): ApiResult<List<Movie>> = withContext(Dispatchers.IO) {
        if (movieDao.movieCount() == 0) {
            val serverResult = api.getPopulardMovies(apiKey).results
            movieDao.insert(
                //converts List to vararg
                *serverResult.map { it.toDbMovie() }.toTypedArray()
            )
        }

        ApiResult.Success(movieDao.getAll().map {
            it.toDomainMovie()
        })
    }

    suspend fun findById(movieId: Int) = withContext(Dispatchers.IO) {
        movieDao.findById(movieId).toDomainMovie()
    }
}