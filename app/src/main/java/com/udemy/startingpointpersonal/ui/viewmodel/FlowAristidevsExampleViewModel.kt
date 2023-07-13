package com.udemy.startingpointpersonal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udemy.startingpointpersonal.domain.FlowExampleUseCase
import com.udemy.startingpointpersonal.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlowAristidevsExampleViewModel @Inject constructor(
    private val bombitasUseCase: FlowExampleUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<PopularMoviesUIState<List<Movie>>>(PopularMoviesUIState.Loading)
    val uiState: StateFlow<PopularMoviesUIState<List<Movie>>> = _uiState


    fun example(){
        viewModelScope.launch {
            bombitasUseCase.counter
                .map { it.toString()}
                .onEach { saveInDB(it) }
                .catch {error ->
                    Log.e("aristiDev", "bombitasError = ${error.message}")
                }
                .collect{bombitas: String ->
                Log.i("aristiDev", "bombitas = $bombitas")
            }

            bombitasUseCase.invoke()
                .catch { _uiState.value = PopularMoviesUIState.Error(it.message ?: "default error message") }
                .flowOn(Dispatchers.IO) // por default el flow se ejecuta en el hilo principal y flowOn se ejecuta en las líneas de arriba, el collect lo ejecuta en ui
                .collect{ movies: List<Movie> ->
                    _uiState.update { PopularMoviesUIState.Success(movies) }
                    //_uiState.value = PopularMoviesUIState.Success(movies)
                }
        }
    }

    private fun saveInDB(info: String) {
        //save in database
    }

}

sealed class PopularMoviesUIState<out T> {
    object Loading: PopularMoviesUIState<Nothing>()
    data class Success<out T>(val list: T): PopularMoviesUIState<T>()
    data class Error(val mensaje: String): PopularMoviesUIState<Nothing>()
}
