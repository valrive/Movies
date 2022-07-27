package com.udemy.startingpointpersonal.ui.popularMovs

import androidx.lifecycle.*
import com.udemy.startingpointpersonal.data.api.ApiResult
import com.udemy.startingpointpersonal.data.pojos.Movie
import com.udemy.startingpointpersonal.domain.GetAllMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.udemy.startingpointpersonal.ui.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase
) : ViewModel() {

    data class UiState(
        val status: Status? = null,
        val movies: List<Movie>? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state//.asStateFlow()


    init {
        _state.update { it.copy(status = Status.LOADING) }

        viewModelScope.launch{
            when (val result = getAllMoviesUseCase()) {
                is ApiResult.Success -> {
                    _state.update { it.copy(status = Status.SUCCESS, movies = result.data) }
                }
/*
                is ApiResult.ErrorSEH -> {
                    _errorPopularMovies.value = result.err
                    _status.value = Status.FAILURE
                }

                is ResourceNew.ErrorEP ->{
                    _errorPopularMovies.value = result.err
                    _status.value = Status.ERROR
                }
*/
                else -> {}
            }

        }
    }


    /**
     * ViewModelScope.coroutineContext + Dispatchers.Main indica que la corutina se ejecutará en el hilo main mientras el view model viva
     * dentro del repo se estarán ejecutando las corrutinas con el dispatcher.IO
     */


}
