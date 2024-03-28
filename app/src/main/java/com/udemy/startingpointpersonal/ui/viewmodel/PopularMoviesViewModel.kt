package com.udemy.startingpointpersonal.ui.viewmodel

import androidx.lifecycle.*
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.domain.model.interfaces.GeneralUseCase
import com.udemy.startingpointpersonal.ui.view.popularMovs.PopularMoviesFragment.Companion.DEFAULT_REGION
import com.udemy.startingpointpersonal.ui.view.popularMovs.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getAllMoviesUseCase: GeneralUseCase,
) : ViewModel() {

    val lastVisible = MutableStateFlow(0)

    private var userRegion = DEFAULT_REGION

    private val _progressBar = MutableStateFlow(true)
    val progressBar: StateFlow<Boolean> get() = _progressBar

    private val _uiState = MutableStateFlow<ViewState<List<Movie>>>(ViewState.Loading)
    val uiState: StateFlow<ViewState<List<Movie>>> get() = _uiState

    val moviesF: Flow<ViewState<List<Movie>>> = getAllMoviesUseCase(userRegion)
    val moviesLD: LiveData<ViewState<List<Movie>>> = getAllMoviesUseCase(userRegion).asLiveData()

    fun getMoviesF(region: String): Flow<ViewState<List<Movie>>> = flow{
        userRegion = region
        getAllMoviesUseCase(userRegion).onStart {
            checkRequireNewPage()
        }.collect{
            emit(it)
        }
    }

    private fun checkRequireNewPage() = viewModelScope.launch {
        lastVisible.collect { lastVisibleItem ->
            getAllMoviesUseCase.checkRequireNewPage(userRegion, lastVisibleItem)
        }
    }


}