package com.udemy.startingpointpersonal.domain

import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.ui.view.popularMovs.PopularMoviesFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FlowExampleUseCase @Inject constructor(
    private val repo: MovieRepository
) {
    val counter: Flow<Int> = flow {
        var bombitas = 0
        while(bombitas < 10){
            delay(1000)
            bombitas += 1
            emit(bombitas)
        }
    }
    suspend operator fun invoke(region: String? = PopularMoviesFragment.DEFAULT_REGION): Flow<List<Movie>> = flow {
        emit(repo.getPopularMovies(region!!))
        delay(1000)
    }
}