package com.udemy.startingpointpersonal

import com.udemy.startingpointpersonal.data.api.MovieRemote
import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import com.udemy.startingpointpersonal.data.database.entity.toDomainMovies
import com.udemy.startingpointpersonal.data.repository.interfaces.LocalDataSource
import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import com.udemy.startingpointpersonal.data.repository.interfaces.RemoteDataSource
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.domain.model.interfaces.GeneralUseCase
import com.udemy.startingpointpersonal.ui.view.popularMovs.ViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

val fakeMovies = listOf(
    // creamos una instancia de MovieRemote con todas las variables
    MovieRemote(id = 1, title = "Movie 1", voteAverage = 5.0, voteCount = 100),
    MovieRemote(id = 2, title = "Movie 2", voteAverage = 5.0, voteCount = 100),
    MovieRemote(id = 3, title = "Movie 3", voteAverage = 5.0, voteCount = 100),
    MovieRemote(id = 4, title = "Movie 4", voteAverage = 5.0, voteCount = 100),
    MovieRemote(id = 5, title = "Movie 5", voteAverage = 5.0, voteCount = 100),

)

class FakeGetAllMoviesUseCase(private val movieRepository: MovieRepository) : GeneralUseCase {

    override operator fun invoke(region: String): Flow<ViewState<List<Movie>>> = movieRepository.getMovies(region)

    override suspend fun checkRequireNewPage( region: String, lastVisible: Int) {
        movieRepository.checkRequireNewPage(region, lastVisible)
    }

}
class FakeLocalDataSource : LocalDataSource {

    private val movies = mutableListOf<Movie>()

    override suspend fun isEmpty(): Boolean = movies.isEmpty()

    override suspend fun size(): Int = movies.size

    override suspend fun saveMovies(movies: List<MovieEntity>) { this.movies += movies.toDomainMovies() }

    override fun getMovies(): Flow<ViewState<List<Movie>>> = flowOf(ViewState.Success(movies))

    override suspend fun findById(movieId: Int): Movie = movies.first { it.id == movieId }

    override suspend fun clearMovies() = movies.clear()

}

class FakeRemoteDataSource(
    private val movies: List<MovieRemote> = emptyList(),
    private val delay: Long = 0
) : RemoteDataSource {

    override suspend fun getPopularMovies(countryCode: String, page: Int): List<MovieRemote> =
        movies.apply{
            delay(delay)
        }

    override suspend fun getPopularMoviesCall(countryCode: String, page: Int): List<MovieRemote> =
        movies.apply{
            delay(delay)
        }

}