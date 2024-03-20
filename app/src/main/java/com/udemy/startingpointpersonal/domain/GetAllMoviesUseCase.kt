package com.udemy.startingpointpersonal.domain

import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.domain.model.interfaces.GeneralUseCase
import com.udemy.startingpointpersonal.ui.view.popularMovs.ViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(
    private val repo: MovieRepository
): GeneralUseCase {

    companion object{
        const val DEFAULT_ERROR_MSG = "default error message"
    }


    override operator fun invoke(region: String): Flow<ViewState<List<Movie>>> = flow{
        emit(ViewState.Loading)
        repo.getMovies(region)
            .catch {
                emit(ViewState.Error(it.message ?: DEFAULT_ERROR_MSG))
            }.collect{
            emit(it)
        }
    }

    override suspend fun checkRequireNewPage(
        region: String,
        lastVisible: Int
    ) = repo.checkRequireNewPage(region, lastVisible)
}
