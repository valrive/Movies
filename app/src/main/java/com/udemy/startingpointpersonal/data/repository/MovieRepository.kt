package com.udemy.startingpointpersonal.data.repository


import com.udemy.startingpointpersonal.data.api.ApiResult
import com.udemy.startingpointpersonal.data.pojos.Movie
import com.udemy.startingpointpersonal.data.toDbMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val moviesLocalDS: MoviesLocalDataSource,
    private val moviesRemoteDS: MoviesRemoteDataSource
) {

    suspend fun getPopularMovies(): ApiResult<List<Movie>> = withContext(Dispatchers.IO) {
        if(moviesLocalDS.isEmpty()){
            val movies = moviesRemoteDS.getPopularMovies()
            moviesLocalDS.save(movies.map { it.toDbMovie() })
        }
        ApiResult.Success(moviesLocalDS.getAll())
    }

    suspend fun findById(movieId: Int) = withContext(Dispatchers.IO) {
        moviesLocalDS.findById(movieId)
    }
}