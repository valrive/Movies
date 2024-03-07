package com.udemy.startingpointpersonal.ui.viewmodel

import androidx.lifecycle.*
import com.udemy.startingpointpersonal.domain.GetAllMoviesUseCase
import com.udemy.startingpointpersonal.ui.view.popularMovs.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
) : ViewModel() {

    companion object{
        private const val DEFAULT_ERROR_MSG = "default error message"
    }

    /**
     * Hay 2 modos de implementar StateFlow al igual que liveData:
     *
     * Primer modo, implementando el builder flow dentro de la misma variable
     */
    val popularMoviesF = flow {
        kotlin.runCatching {
            getAllMoviesUseCase().collect{
                emit(ViewState.Success(it))
            }
        }.onSuccess {
        }.onFailure {error ->
            emit(ViewState.Error(error.message ?: DEFAULT_ERROR_MSG))
        }
    }

    val popularMoviesLD = popularMoviesF.asLiveData()








    /**
     * Segundo modo, usando las variables separadas y creando un método que detone la actualización de _state
     */
    private val _uiState = MutableStateFlow<ViewState<*>>(ViewState.Loading)
    val getPopularMoviesSF: StateFlow<ViewState<*>> = _uiState
    val getPopularMoviesLD = _uiState.asLiveData()//.asStateFlow()
    fun initGetPopularMovies() {
        _uiState.update { it }
        viewModelScope.launch{
            kotlin.runCatching {
                getAllMoviesUseCase()
            }.onSuccess { movies ->
                _uiState.update { ViewState.Success(movies) }
            }.onFailure { error ->
                _uiState.value = ViewState.Error(error.message ?: DEFAULT_ERROR_MSG)
            }

        }
    }









    fun fetchPopularMoviesLD(region: String) = liveData {
        emit(ViewState.Loading)
        kotlin.runCatching {
            getAllMoviesUseCase(region)
        }.onSuccess {movies ->
            emit(ViewState.Success(movies))
        }.onFailure {error ->
            emit(ViewState.Error(error.message ?: DEFAULT_ERROR_MSG))
        }

    }

    fun fetchPopularMoviesSF(region: String) =
        fetchPopularMoviesLD(region).asFlow()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = ViewState.Loading
                )
    /**
     * la función de abajo es reemplazada por la función de arriba para demostrar la conversión de un livedata a flow
     */
    /*fun fetchPopularMoviesFlow(region: String) = flow {
        kotlin.runCatching {
            getAllMoviesUseCase(region)
        }.onSuccess {movies ->
            emit(PopularMoviesUIState.Success(movies))
        }.onFailure {error ->
            emit(PopularMoviesUIState.Error(error.message ?: DEFAULT_ERROR_MSG))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PopularMoviesUIState.Loading
    )*/



}