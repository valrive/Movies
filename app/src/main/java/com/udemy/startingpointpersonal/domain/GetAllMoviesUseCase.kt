package com.udemy.startingpointpersonal.domain

import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.ui.view.popularMovs.PopularMoviesFragment
import kotlinx.coroutines.flow.Flow

class GetAllMoviesUseCase( private val repo: MovieRepository) {


    operator fun invoke(region: String? = PopularMoviesFragment.DEFAULT_REGION): Flow<List<Movie>> {
        val movies = repo.getMovies(region!!)
        return movies
    }
}