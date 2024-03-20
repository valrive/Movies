package com.udemy.startingpointpersonal.domain.model.interfaces

import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.ui.view.popularMovs.PopularMoviesFragment
import com.udemy.startingpointpersonal.ui.view.popularMovs.ViewState
import kotlinx.coroutines.flow.Flow

interface GeneralUseCase {

    operator fun invoke(
        region: String = PopularMoviesFragment.DEFAULT_REGION
    ): Flow<ViewState<List<Movie>>>

    suspend fun checkRequireNewPage(
        region: String = PopularMoviesFragment.DEFAULT_REGION,
        lastVisible: Int
    )
}