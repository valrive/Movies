package com.udemy.startingpointpersonal.data.repository

import com.udemy.startingpointpersonal.data.api.ApiResult
import com.udemy.startingpointpersonal.data.pojos.Movie
import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import com.udemy.startingpointpersonal.data.toDbMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val moviesLocalDS: MoviesLocalDataSourceImpl,
    private val moviesRemoteDS: MoviesRemoteDataSourceImpl
): MovieRepository {
    override suspend fun getPopularMovies(region: String): ApiResult<List<Movie>> = withContext(Dispatchers.IO) {
        //if(moviesLocalDS.isEmpty()){
            val movies = moviesRemoteDS.getPopularMovies(region)
            moviesLocalDS.save(movies.map { it.toDbMovie() })
        //}
        ApiResult.Success(moviesLocalDS.getAll())
    }

    override suspend fun getPopularMoviesCall(region: String): ApiResult<List<Movie>> = withContext(Dispatchers.IO){
        val movies = moviesRemoteDS.getPopularMoviesCall(region)
        moviesLocalDS.save(movies.map { it.toDbMovie() })
        ApiResult.Success(moviesLocalDS.getAll())
    }

    override suspend fun findById(movieId: Int) = withContext(Dispatchers.IO) {
        moviesLocalDS.findById(movieId)
    }

}