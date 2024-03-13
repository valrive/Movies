package com.udemy.startingpointpersonal.domain

import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.ui.view.popularMovs.PopularMoviesFragment
import com.udemy.startingpointpersonal.ui.view.popularMovs.ViewState
import com.udemy.startingpointpersonal.ui.viewmodel.PopularMoviesViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetAllMoviesUseCase( private val repo: MovieRepository) {

    companion object{
        const val DEFAULT_ERROR_MSG = "default error message"
    }


    operator fun invoke(region: String? = PopularMoviesFragment.DEFAULT_REGION): Flow<ViewState<List<Movie>>> = flow{
        emit(ViewState.Loading)
        repo.getMovies(region!!)
            .catch {
                emit(ViewState.Error(it.message ?: DEFAULT_ERROR_MSG))
            }.collect{
            emit(it)
        }
    }

    suspend fun checkRequireNewPage(
        region: String? = PopularMoviesFragment.DEFAULT_REGION,
        lastVisible: Int) =
        repo.checkRequireNewPage(region!!, lastVisible)
}