package com.udemy.startingpointpersonal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.udemy.startingpointpersonal.domain.GetAllMoviesUseCase
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.ui.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase
) : ViewModel() {

    data class UiState(
        //Estados iniciales
        val status: Status = Status.LOADING, //todo: Cambiarlo a ApiResults?
        val movies: List<Movie> = emptyList(),
        val error: Throwable? = null
    )

    /**
     * Hay 2 modos de implementar StateFlow al igual que liveData:
     *
     * Primer modo, implementando el builder flow dentro de la misma variable
     */
    val popularMovies: StateFlow<UiState> = flow {
        kotlin.runCatching {
            getAllMoviesUseCase()
        }.onSuccess {
            emit(UiState(status = Status.SUCCESS, movies = it))
        }.onFailure {
            emit(UiState(status = Status.FAILURE, error = it))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState(status = Status.LOADING)
    )


    /**
     * Segundo modo, usando las variables separadas y creando un método que detone la actualización del mutableStateFlow
     */
    private val _state = MutableStateFlow(UiState())
    val getPopularMovies: StateFlow<UiState> = _state//.asStateFlow()

    /*init {
        _state.update { it }
        viewModelScope.launch{
            kotlin.runCatching {
                getAllMoviesUseCase()
            }.onSuccess { movies ->
                _state.update { UiState(status = Status.SUCCESS, movies = movies)
                }
            }.onFailure { error ->
                _state.update { UiState(status = Status.FAILURE, error = error) }
            }

        }
    }*/


    /**
     * 3ra opción: Mediante un método que pueda recibir algún parámetro
     */
    fun fetchPopularMoviesLive(region: String) = liveData {
        emit(UiState(status = Status.LOADING))
        kotlin.runCatching {
            getAllMoviesUseCase(region)
        }.onSuccess {
            emit(UiState(status = Status.SUCCESS, movies = it))
        }.onFailure {
            emit(UiState(status = Status.FAILURE, error = it))
        }

    }

    fun fetchPopularMoviesFlow(region: String) = flow {
        kotlin.runCatching {
            getAllMoviesUseCase(region)
        }.onSuccess {
            emit(UiState(status = Status.SUCCESS, movies = it))
        }.onFailure {
            emit(UiState(status = Status.FAILURE, error = it))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState(status = Status.LOADING)
    )

}
