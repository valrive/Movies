package com.udemy.startingpointpersonal.domain

import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import com.udemy.startingpointpersonal.ui.view.popularMovs.PopularMoviesFragment
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(
    private val repo: MovieRepository
){
    suspend operator fun invoke(region: String? = PopularMoviesFragment.DEFAULT_REGION) = repo.getPopularMovies(region!!)
}