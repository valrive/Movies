package com.udemy.startingpointpersonal.ui.viewmodel

import androidx.lifecycle.*
import com.udemy.startingpointpersonal.domain.GetAllMoviesUseCase
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.ui.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
) : ViewModel() {

    data class PopularMoviesUiState(
        //todo: hacer genérico T para que launchAndCollect también sea T
        //Estado inicial
        val status: Status = Status.LOADING, //todo: Cambiarlo a ApiResults?
        val movies: List<Movie> = emptyList(),
        val error: Throwable? = null
    )





    /**
     * Hay 2 modos de implementar StateFlow al igual que liveData:
     *
     * Primer modo, implementando el builder flow dentro de la misma variable
     */
    val popularMoviesF = flow {
        kotlin.runCatching {
            getAllMoviesUseCase()
        }.onSuccess {
            emit(PopularMoviesUiState(status = Status.SUCCESS, movies = it))
        }.onFailure {
            emit(PopularMoviesUiState(status = Status.FAILURE, error = it))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PopularMoviesUiState(status = Status.LOADING)
    )

    val popularMoviesLD = popularMoviesF.asLiveData()








    /**
     * Segundo modo, usando las variables separadas y creando un método que detone la actualización de _state
     */
    private val _state = MutableStateFlow(PopularMoviesUiState())
    val getPopularMoviesSF: StateFlow<PopularMoviesUiState> = _state
    val getPopularMoviesLD = _state.asLiveData()//.asStateFlow()
    fun initGetPopularMovies() {
        _state.update { it }
        viewModelScope.launch{
            kotlin.runCatching {
                getAllMoviesUseCase()
            }.onSuccess { movies ->
                _state.update { PopularMoviesUiState(status = Status.SUCCESS, movies = movies)
                }
            }.onFailure { error ->
                _state.update { PopularMoviesUiState(status = Status.FAILURE, error = error) }
            }

        }
    }









    fun fetchPopularMoviesLD(region: String) = liveData {
        emit(PopularMoviesUiState(status = Status.LOADING))
        kotlin.runCatching {
            getAllMoviesUseCase(region)
        }.onSuccess {
            emit(PopularMoviesUiState(status = Status.SUCCESS, movies = it))
        }.onFailure {
            emit(PopularMoviesUiState(status = Status.FAILURE, error = it))
        }

    }

    fun fetchPopularMoviesSF(region: String) =
        fetchPopularMoviesLD(region).asFlow()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = PopularMoviesUiState(status = Status.LOADING)
                )
    /**
     * la función de abajo es reemplazada por la función de arriba para demostrar la conversión de un livedata a flow
     */
    /*fun fetchPopularMoviesFlow(region: String) = flow {
        kotlin.runCatching {
            getAllMoviesUseCase(region)
        }.onSuccess {
            emit(PopularMoviesUiState(status = Status.SUCCESS, movies = it))
        }.onFailure {
            emit(PopularMoviesUiState(status = Status.FAILURE, error = it))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PopularMoviesUiState(status = Status.LOADING)
    )*/



}
