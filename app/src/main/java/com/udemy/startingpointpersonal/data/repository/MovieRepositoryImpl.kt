package com.udemy.startingpointpersonal.data.repository

import com.udemy.startingpointpersonal.data.api.ApiResult
import com.udemy.startingpointpersonal.data.model.Movie
import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import com.udemy.startingpointpersonal.data.repository.interfaces.MoviesLocalDataSource
import com.udemy.startingpointpersonal.data.repository.interfaces.MoviesRemoteDataSource
import com.udemy.startingpointpersonal.data.toDbMovie
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieLocalDS: MoviesLocalDataSource,
    private val movieRemoteDS: MoviesRemoteDataSource,
    private val movieProvider: MovieProvider
): MovieRepository {

    override suspend fun getPopularMovies(region: String): ApiResult<List<Movie>> {
        val movies = movieRemoteDS.getPopularMoviesCall(region)

        //Versión que guarda directo en una clase sin pasar por ROOM ni Preferencias
        movieProvider.movies = movies.map { it.toDbMovie() }
        //return ApiResult.Success(movieProvider.movies.map { it.toDomainMovie() })

        movieLocalDS.save(movies.map { it.toDbMovie() })
        return ApiResult.Success(movieLocalDS.getAll())
    }

    override suspend fun findById(movieId: Int) = movieLocalDS.findById(movieId)

}