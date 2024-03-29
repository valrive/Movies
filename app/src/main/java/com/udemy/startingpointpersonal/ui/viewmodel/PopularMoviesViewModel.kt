package com.udemy.startingpointpersonal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.domain.model.interfaces.GeneralUseCase
import com.udemy.startingpointpersonal.ui.view.popularMovs.PopularMoviesFragment.Companion.DEFAULT_REGION
import com.udemy.startingpointpersonal.ui.view.popularMovs.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getAllMoviesUseCase: GeneralUseCase,
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _paginationLoading = MutableStateFlow(false)
    val paginationLoading = _paginationLoading.asStateFlow()

    val lastVisible = MutableStateFlow(0)
    private var userRegion = DEFAULT_REGION



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
            _paginationLoading.value = true
            Log.d("PopularMoviesViewModel", "lastVisibleItem: $lastVisibleItem")
            val time = measureTimeMillis {
                delay(1_000)
                runCatching {
                    getAllMoviesUseCase.checkRequireNewPage(userRegion, lastVisibleItem)
                }.onFailure {
                    Log.e("PopularMoviesViewModel", "Error: ${it.message}")
                }
            }
            Log.d("PopularMoviesViewModel", "api call duration: $time")
            _paginationLoading.value = false
        }
    }


}