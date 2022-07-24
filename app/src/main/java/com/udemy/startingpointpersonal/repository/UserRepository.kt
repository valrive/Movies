package com.udemy.startingpointpersonal.repository

import android.util.Base64
import android.util.Log
import com.udemy.startingpointpersonal.api.ApiResults
import com.udemy.startingpointpersonal.api.ApiService
import com.udemy.startingpointpersonal.dao.UserDao
import com.udemy.startingpointpersonal.pojos.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Jorge Segundo on 2019-07-30.
 */
class UserRepository @Inject constructor(
    private val userDao: UserDao,
    val api: ApiService
){

    fun getCurrentUser() = userDao.findCurrent()

    fun saveUser(user: User) = userDao.create(user)

    fun logout() = userDao.deleteAll()

    fun authorize(user: User, onSuccess: () -> Unit, onError: (t: Throwable) -> Unit) {
        Log.d(TAG,"authorize")
        onSuccess()
    }

    /**
     * Generate authorization header for API requests
     * @param user: user
     * @return Authorization header
     */
    private fun generateAuthHeader(user: User): String {
        val bytes = String.format("%s:%s", user.username, user.password).toByteArray()
        return "Basic " + Base64.encodeToString(bytes, Base64.NO_WRAP)
    }

    companion object {
        const val TAG = "UserRepository"
    }
}