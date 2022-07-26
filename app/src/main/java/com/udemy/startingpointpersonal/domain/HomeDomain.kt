package com.udemy.startingpointpersonal.domain

import com.udemy.startingpointpersonal.repository.MovieRepository
import javax.inject.Inject

class HomeDomain @Inject constructor(
    private val repo: MovieRepository
){

    suspend fun getPopularMovies() =  repo.getPopularMovies()

    companion object {
        const val TAG = "HomeDomain"
    }
}