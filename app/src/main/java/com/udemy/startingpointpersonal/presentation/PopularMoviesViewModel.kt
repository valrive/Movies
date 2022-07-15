package com.udemy.startingpointpersonal.presentation

import androidx.lifecycle.*
import com.udemy.startingpointpersonal.core.ApiResult
import com.udemy.startingpointpersonal.domain.HomeDomain
import com.udemy.startingpointpersonal.repository.MovieRepository
import com.udemy.startingpointpersonal.utils.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.udemy.startingpointpersonal.core.ResourceNew
import com.udemy.startingpointpersonal.core.ServiceErrorHandler
import com.udemy.startingpointpersonal.pojos.MovieList
import com.udemy.startingpointpersonal.ui.Status
import kotlinx.coroutines.launch

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val repo: MovieRepository,
    private val homeDomain: HomeDomain,
    private val exceptionParser: ExceptionParser
) : ViewModel() {

    private val _logoutBtn = MutableLiveData<Boolean>()
    val logoutBtn: LiveData<Boolean> = _logoutBtn

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    private val _getPopularMovies = MutableLiveData<MovieList>()
    val getPopularMovies: LiveData<MovieList> = _getPopularMovies
    private val _errorPopularMovies = MutableLiveData<ServiceErrorHandler?>()
    val errorPopularMovies: LiveData<ServiceErrorHandler?> = _errorPopularMovies


    init {
       _status.value = Status.LOADING
        viewModelScope.launch {
            when (val result = repo.getPopularMovies()) {
                is ApiResult.Success -> {
                    _getPopularMovies.value = result.data
                    _status.value = Status.SUCCESS
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


    fun onLogoutClick() {
        _status.value = Status.LOADING
        homeDomain.logout()
        _status.value = Status.SUCCESS
        _logoutBtn.value = true
    }

}
