package com.udemy.startingpointpersonal.data.repository

import android.util.Log
import com.udemy.startingpointpersonal.data.api.toDomainMovies
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import com.udemy.startingpointpersonal.data.repository.interfaces.LocalDataSource
import com.udemy.startingpointpersonal.data.repository.interfaces.RemoteDataSource
import com.udemy.startingpointpersonal.ui.view.popularMovs.ViewState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): MovieRepository {

    companion object{
        private const val PAGE_SIZE = 20
        private const val PAGE_THRESHOLD = 4
    }

    override fun getMovies(region: String): Flow<ViewState<List<Movie>>> = localDataSource.getMovies()

    override suspend fun findById(movieId: Int) = localDataSource.findById(movieId)

    override suspend fun clearMovies() = localDataSource.clearMovies()

    override suspend fun insertMovies(list: List<Movie>) {
        //Versión que guarda directo en una clase sin pasar por ROOM ni Preferencias
        //movieProvider.movies = movies.map { it.toEntityMovie() }
        //return movieProvider.movies.map { it.toDomainMovie() }
        localDataSource.saveMovies(list)
    }

    override suspend fun checkRequireNewPage(region: String, lastVisible: Int){
        val size = localDataSource.size()
        if(lastVisible >= size - PAGE_THRESHOLD){
            val page = size / PAGE_SIZE + 1
            val newMovies = remoteDataSource.getPopularMoviesCall(region, page).toDomainMovies()
            localDataSource.saveMovies(newMovies)
            Log.d("MovieRepositoryImpl", "API pagination: $page")
            Log.d("MovieRepositoryImpl", "movies size: ${size + newMovies.size}")
        }
    }
}