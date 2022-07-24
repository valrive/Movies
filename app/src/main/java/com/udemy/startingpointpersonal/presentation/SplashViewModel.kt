package com.udemy.startingpointpersonal.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.udemy.startingpointpersonal.core.ApiResult
import com.udemy.startingpointpersonal.domain.UserDomain
import com.udemy.startingpointpersonal.utils.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor (
    private val userDomain: UserDomain,
    private val exceptionParser: ExceptionParser
    ) : ViewModel() {

    /**
    * Returns true if user is already logged in
    */
    fun fetchLoggedIn() = liveData {
        try{
            emit(ApiResult.Success(true))
        } catch(e: Exception){
            emit(ApiResult.Failure(e))
        }

        //runCatching
    }
}