package com.udemy.startingpointpersonal.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udemy.startingpointpersonal.core.Result
import com.udemy.startingpointpersonal.domain.UserDomain
import com.udemy.startingpointpersonal.pojos.User
import com.udemy.startingpointpersonal.utils.ExceptionParser
import com.udemy.startingpointpersonal.utils.RequiredFieldException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDomain: UserDomain,
    private val exceptionParser: ExceptionParser,
    @Named("environment") val version: String
) : ViewModel() {

    var username: String? = null
    var password: String? = null

    private val _progressVisibility = MutableLiveData<Boolean>()
    val progressVisibility: LiveData<Boolean> get() = _progressVisibility

    private val _userLogged = MutableLiveData<Result<Unit>>()
    val userLogged: LiveData<Result<Unit>> = _userLogged

    fun onClick() = viewModelScope.launch{//(viewModelScope.coroutineContext + Dispatchers.Main) {
        _progressVisibility.value = true

        val loginUser = User(
            username = username,
            password = password
        )

        runCatching {
            // validate required fields
            //Validator.validateForm(loginUser)

            userDomain.login(loginUser)
        }.onFailure {
            _progressVisibility.value = false
            if (it is RequiredFieldException) {
                _userLogged.value = Result.Failure(exceptionParser.toPresentableMessage(it))
                return@launch
            }
            _userLogged.value = Result.Success(Unit)
        }.onSuccess {
            _progressVisibility.value = false
            _userLogged.value = Result.Success(Unit)
        }
    }

}