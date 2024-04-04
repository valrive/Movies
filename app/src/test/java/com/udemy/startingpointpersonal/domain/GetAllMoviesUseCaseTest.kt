package com.udemy.startingpointpersonal.domain

import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import com.udemy.startingpointpersonal.domain.model.Movie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetAllMoviesUseCaseTest {

    /**
     * Es la anotación ideal para que no autogenere salidas que nos faltan
     */
    //@MockK
    /**
     * Si nosotros no definimos una de las respuestas de la clase que estamos testeando,
     * se nos va a generar automáticamente
     */
    @RelaxedMockK
    private lateinit var movieRepository: MovieRepository

    lateinit var getAllMoviesUseCase: GetAllMoviesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getAllMoviesUseCase = GetAllMoviesUseCase(movieRepository)
    }

    @Test
    fun `minimal check test`(){
        Assert.assertTrue(true)
    }
/*
    @Test
    fun `when the api doesnt return anything then get values from database`() = runBlocking {
        //Given
        //Si la siguiente fun no estuviese en una corutina entonces se debe usar every{} en lugar de coEvery{}
        //coEvery { movieRepository.getMovies("US") } returns emptyList()

        //When
        getAllMoviesUseCase()

        //Then
        coVerify(exactly = 0) { movieRepository.clearMovies() }
        coVerify(exactly = 0) { movieRepository.insertMovies(any()) }
        coVerify(exactly = 1) { movieRepository.getMovies(any()) }
    }

    @Test
    fun `when the api return something then get values from api`() = runBlocking {
        //Given
        val myList = listOf(Movie(id = 19, title = "Anaconda", voteAverage = 0.0, voteCount = 0))
        //coEvery { movieRepository.getMovies("US") } returns myList

        //When
        val response = getAllMoviesUseCase()

        //Then
        coVerify(exactly = 1) { movieRepository.clearMovies() }
        coVerify(exactly = 1) { movieRepository.insertMovies(any()) }
        //coVerify(exactly = 0) { movieRepository.getAllMovies() }
        assert(myList == response)

    }*/

}