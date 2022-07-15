package com.udemy.startingpointpersonal.presentation

import androidx.lifecycle.*
import com.udemy.startingpointpersonal.domain.HomeDomain
import com.udemy.startingpointpersonal.repository.MovieRepository
import com.udemy.startingpointpersonal.utils.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import com.udemy.startingpointpersonal.core.ApiResult
import com.udemy.startingpointpersonal.core.ServiceErrorHandler
import com.udemy.startingpointpersonal.pojos.MovieList
import com.udemy.startingpointpersonal.ui.Status
import kotlinx.coroutines.launch

@HiltViewModel
class AllMoviesViewModel @Inject constructor(
    private val repo: MovieRepository,
    private val homeDomain: HomeDomain,
    private val exceptionParser: ExceptionParser
) : ViewModel() {

    private val _logoutBtn = MutableLiveData<Boolean>()
    val logoutBtn: LiveData<Boolean> = _logoutBtn

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    private val _getAllMovies = MutableLiveData<Triple<MovieList, MovieList, MovieList>>()
    val getAllMovies: LiveData<Triple<MovieList, MovieList, MovieList>> = _getAllMovies
    private val _errorAllMovies = MutableLiveData<ServiceErrorHandler?>()
    val errorAllMovies: LiveData<ServiceErrorHandler?> = _errorAllMovies


    init {
        _status.value = Status.LOADING
        viewModelScope.launch {
            //val result = repo.getPopularMovies()
            _getAllMovies.value = Triple(
                repo.getUpcomingMovies(),
                repo.getTopRatedMovies(),
                repo.getPopularMovies()
            )
            _status.value = Status.SUCCESS
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