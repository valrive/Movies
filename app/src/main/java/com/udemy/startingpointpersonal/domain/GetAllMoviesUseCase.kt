package com.udemy.startingpointpersonal.domain

import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import com.udemy.startingpointpersonal.data.toEntityMovie
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.ui.view.popularMovs.PopularMoviesFragment
import javax.inject.Inject

class GetAllMoviesUseCase( private val repo: MovieRepository) {
    suspend operator fun invoke(region: String? = PopularMoviesFragment.DEFAULT_REGION): List<Movie> {
        val movies = repo.getPopularMovies(region!!)

        return if (movies.isNotEmpty()) {
            repo.clearMovies()
            repo.insertMovies(movies.map { it.toEntityMovie() })
            movies
        } else {
            repo.getAllMovies()
        }
    }
}