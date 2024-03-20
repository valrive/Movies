package com.udemy.startingpointpersonal.ui.viewmodel

import com.udemy.startingpointpersonal.CoroutineTestRule
import com.udemy.startingpointpersonal.FakeGetAllMoviesUseCase
import com.udemy.startingpointpersonal.FakeLocalDataSource
import com.udemy.startingpointpersonal.FakeRemoteDataSource
import com.udemy.startingpointpersonal.data.api.toDomainMovie
import com.udemy.startingpointpersonal.data.repository.MovieRepositoryImpl
import com.udemy.startingpointpersonal.fakeMovies
import com.udemy.startingpointpersonal.ui.view.popularMovs.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PopularMoviesViewModelTest{

    //regla para que las corutinas se ejecuten en un hilo de testing
    //este val se usa para reusar código que se ejecuta antes y después de cada test
    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    //Confirmamos que el test funciona correctamente
    // runBlockingTest es para que el test no termine hasta que todas las corutinas hayan terminado
    @Test
    fun `listening movies flow emits the list of movies from server`() = coroutinesTestRule.testDispatcher.runBlockingTest {

        val repository = MovieRepositoryImpl(
            FakeLocalDataSource(),
            FakeRemoteDataSource(movies = fakeMovies)
        )
        val useCase = FakeGetAllMoviesUseCase(repository)
        val viewModel = PopularMoviesViewModel(useCase)
        viewModel.moviesF.collect{ viewState ->
            Assert.assertEquals(viewState, ViewState.Success(fakeMovies.map { it.toDomainMovie() }))
        }
    }


    @Test(expected = TimeoutCancellationException::class)
    fun `After timeout, an exception is thrown`() = coroutinesTestRule.testDispatcher.runBlockingTest {

        val repository = MovieRepositoryImpl(
            FakeLocalDataSource(),
            FakeRemoteDataSource(delay = 6_000)
        )
       repository.checkRequireNewPage("US", 0)
    }





}