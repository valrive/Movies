package com.udemy.startingpointpersonal.domain

import com.udemy.startingpointpersonal.data.repository.MovieRepository
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(
    private val repo: MovieRepository
){
    suspend operator fun invoke() = repo.getPopularMovies()
}