package com.udemy.startingpointpersonal.presentation

import androidx.lifecycle.*
import com.udemy.startingpointpersonal.core.ApiResult
import com.udemy.startingpointpersonal.domain.HomeDomain
import com.udemy.startingpointpersonal.repository.MovieRepository
import com.udemy.startingpointpersonal.utils.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: MovieRepository,
    private val homeDomain: HomeDomain,
    private val exceptionParser: ExceptionParser
) : ViewModel() {

    private val _logoutBtn = MutableLiveData<ApiResult<Unit>>()
    val logoutBtn: LiveData<ApiResult<Unit>> = _logoutBtn


    /**
     * ViewModelScope.coroutineContext + Dispatchers.Main indica que la corutina se ejecutará en el hilo main mientras el view model viva
     * dentro del repo se estarán ejecutando las corrutinas con el dispatcher.IO
     */
    fun fetchMainScreenMovies() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(ApiResult.Loading)
        try {
            emit(
                ApiResult.Success(
                    Triple(
                        repo.getUpcomingMovies(),
                        repo.getTopRatedMovies(),
                        repo.getTopRatedMovies()
                        //repo.getPopularMovies()
                    )
                )
            )
        } catch (e: Exception){
            emit(ApiResult.Failure(e))
        }
    }

    fun onLogoutClick() {
        _logoutBtn.value = ApiResult.Loading
        // do logout
        homeDomain.logout()
        _logoutBtn.value = ApiResult.Success(Unit)
    }
}