package com.udemy.startingpointpersonal.data.repository

import com.udemy.startingpointpersonal.data.api.MovieRemote
import com.udemy.startingpointpersonal.data.api.toDomainMovies
import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import com.udemy.startingpointpersonal.data.repository.interfaces.LocalDataSource
import com.udemy.startingpointpersonal.data.repository.interfaces.RemoteDataSource
import com.udemy.startingpointpersonal.domain.model.toEntityMovies
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val movieProvider: MovieProvider
): MovieRepository {

    override fun getMovies(region: String): Flow<List<Movie>> = flow{
        if(localDataSource.isEmpty()){
            val moviesRemote: List<MovieRemote> = remoteDataSource.getPopularMoviesCall(region)
            val moviesDomain: List<Movie> = moviesRemote.toDomainMovies()
            val moviesEntity: List<MovieEntity> = moviesDomain.toEntityMovies()
            localDataSource.saveMovies(moviesEntity)
        }

        localDataSource.getMovies().collect{ movies: List<Movie> ->
            while(true){
                delay(2000)
                emit(movies.shuffled())
            }
        }
    }

    override suspend fun findById(movieId: Int) = localDataSource.findById(movieId)

    override suspend fun clearMovies() = localDataSource.clearMovies()

    override suspend fun insertMovies(list: List<MovieEntity>) {
        //Versión que guarda directo en una clase sin pasar por ROOM ni Preferencias
        //movieProvider.movies = movies.map { it.toEntityMovie() }
        //return movieProvider.movies.map { it.toDomainMovie() }

        localDataSource.saveMovies(list)
    }
}