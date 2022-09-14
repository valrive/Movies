package com.udemy.startingpointpersonal.data.repository

import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import com.udemy.startingpointpersonal.data.repository.interfaces.MoviesLocalDataSource
import com.udemy.startingpointpersonal.data.repository.interfaces.MoviesRemoteDataSource
import com.udemy.startingpointpersonal.data.toDomainMovie
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieLocalDS: MoviesLocalDataSource,
    private val movieRemoteDS: MoviesRemoteDataSource,
    private val movieProvider: MovieProvider
): MovieRepository {

    override suspend fun getPopularMovies(region: String): List<Movie> {
        val movies = movieRemoteDS.getPopularMoviesCall(region)
        return movies.map { it.toDomainMovie() }
    }

    override suspend fun findById(movieId: Int) = movieLocalDS.findById(movieId)

    override suspend fun clearMovies() = movieLocalDS.clearMovies()

    override suspend fun insertMovies(list: List<MovieEntity>) {
        //Versión que guarda directo en una clase sin pasar por ROOM ni Preferencias
        //movieProvider.movies = movies.map { it.toEntityMovie() }
        //return movieProvider.movies.map { it.toDomainMovie() }

        movieLocalDS.insert(list)
    }

    override suspend fun getAllMovies(): List<Movie> = movieLocalDS.getAll()
}