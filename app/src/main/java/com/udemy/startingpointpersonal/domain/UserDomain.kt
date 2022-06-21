package com.udemy.startingpointpersonal.domain

import com.udemy.startingpointpersonal.pojos.User
import com.udemy.startingpointpersonal.repository.UserRepository
import javax.inject.Inject

class UserDomain @Inject constructor(
    private val userRepository: UserRepository
){

    fun isLoggedIn() = userRepository.isLoggedIn()

    fun getCurrentUser() = userRepository.getCurrentUser()

    suspend fun login(user: User){
        val userResponse = userRepository.login(user)

        // save current user
        userRepository.saveUser(userResponse)
    }

    fun logout() {
        userRepository.logout()
    }

    fun clearUserData() {
        userRepository.clearUserData()
    }

    companion object {
        const val TAG = "UserDomain"
    }
}