package com.udemy.startingpointpersonal.ui.viewmodel

import androidx.lifecycle.*
import com.udemy.startingpointpersonal.domain.GetAllMoviesUseCase
import com.udemy.startingpointpersonal.domain.GetAllMoviesUseCase.Companion.DEFAULT_ERROR_MSG
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.ui.view.popularMovs.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
) : ViewModel() {

    private val _progressBar = MutableStateFlow(true)
    val progressBar: StateFlow<Boolean> get() = _progressBar

    private val _uiState = MutableStateFlow<ViewState<List<Movie>>>(ViewState.Loading)
    val uiState: StateFlow<ViewState<List<Movie>>> get() = _uiState

    val lastVisible = MutableStateFlow(0)

    val moviesF: Flow<ViewState<List<Movie>>> = getAllMoviesUseCase()
    val moviesLD: LiveData<ViewState<List<Movie>>> = getAllMoviesUseCase().asLiveData()


    init {
        viewModelScope.launch {
            lastVisible.collect{
                notifyLastVisibleItem(it)
            }
        }
    }

    private suspend fun notifyLastVisibleItem(lastVisibleItem: Int) =
        getAllMoviesUseCase.checkRequireNewPage(lastVisible = lastVisibleItem)


}