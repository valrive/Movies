package com.udemy.startingpointpersonal.data.repository.interfaces

import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.ui.view.popularMovs.ViewState
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(region: String): Flow<ViewState<List<Movie>>>

    suspend fun findById(movieId: Int): Movie

    suspend fun clearMovies()

    suspend fun insertMovies(list: List<Movie>)

    suspend fun checkRequireNewPage(region: String, lastVisible: Int)


}