package com.udemy.startingpointpersonal.ui.popularMovs

import android.util.Log
import androidx.lifecycle.*
import com.udemy.startingpointpersonal.data.api.ApiResult
import com.udemy.startingpointpersonal.data.pojos.Movie
import com.udemy.startingpointpersonal.domain.GetAllMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.udemy.startingpointpersonal.ui.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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
    val popularMovies : StateFlow<UiState> = flow{
        kotlin.runCatching {
            getAllMoviesUseCase()
        }.onSuccess {
            emit(UiState(status = Status.SUCCESS, movies = (it as ApiResult.Success).data))
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
            when (val result = getAllMoviesUseCase()) {
                is ApiResult.Success -> {
                    _state.update { it.copy(status = Status.SUCCESS, movies = result.data) }
                }
*//*
                is ApiResult.ErrorSEH -> {
                    _errorPopularMovies.value = result.err
                    _status.value = Status.FAILURE
                }

                is ResourceNew.ErrorEP ->{
                    _errorPopularMovies.value = result.err
                    _status.value = Status.ERROR
                }
*//*
                else -> {}
            }

        }
    }*/


    /**
     * 3ra opción: Mediante un método que pueda recibir algún parámetro
     */
    fun fetchPopularMoviesLive(region: String) = liveData{
        emit(UiState(status = Status.LOADING))
        kotlin.runCatching {
            getAllMoviesUseCase(region)
        }.onSuccess {
            emit(UiState(status = Status.SUCCESS, movies = (it as ApiResult.Success).data))
        }.onFailure {
            emit(UiState(status = Status.FAILURE, error = it))
        }

    }

    fun fetchPopularMoviesFlow(region: String) = flow{
        kotlin.runCatching {
            getAllMoviesUseCase(region)
        }.onSuccess {
            emit(UiState(status = Status.SUCCESS, movies = (it as ApiResult.Success).data))
        }.onFailure {
            emit(UiState(status = Status.FAILURE, error = it))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState(status = Status.LOADING)
    )




    /**
     * ViewModelScope.coroutineContext + Dispatchers.Main indica que la corutina se ejecutará en el hilo main mientras el view model viva
     * dentro del repo se estarán ejecutando las corrutinas con el dispatcher.IO
     */


}
