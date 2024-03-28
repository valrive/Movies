package com.udemy.startingpointpersonal.data.repository

import android.content.Context
import android.widget.Toast
import com.udemy.startingpointpersonal.data.api.toEntityMovies
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import com.udemy.startingpointpersonal.data.repository.interfaces.LocalDataSource
import com.udemy.startingpointpersonal.data.repository.interfaces.RemoteDataSource
import com.udemy.startingpointpersonal.domain.model.toEntityMovies
import com.udemy.startingpointpersonal.ui.view.popularMovs.ViewState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    @ApplicationContext val context: Context
): MovieRepository {

    companion object{
        private const val PAGE_SIZE = 20
        private const val PAGE_THRESHOLD = 4
    }

    override fun getMovies(region: String): Flow<ViewState<List<Movie>>> = localDataSource.getMovies()

    override suspend fun findById(movieId: Int) = localDataSource.findById(movieId)

    override suspend fun clearMovies() = localDataSource.clearMovies()

    override suspend fun insertMovies(list: List<Movie>) = localDataSource.saveMovies(list.toEntityMovies())

    override suspend fun checkRequireNewPage(region: String, lastVisible: Int){
        val size = localDataSource.size()
        if(lastVisible >= size - PAGE_THRESHOLD){
            val page = size / PAGE_SIZE + 1
            val newMovies = withTimeout(5_000) { remoteDataSource.getPopularMoviesCall(region, page).toEntityMovies() }
            localDataSource.saveMovies(newMovies)
            Toast.makeText(context, "region: $region / page: $page  / size: ${size + newMovies.size}", Toast.LENGTH_SHORT).show()
        }
    }
}