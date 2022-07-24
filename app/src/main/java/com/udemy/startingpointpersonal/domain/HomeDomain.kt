package com.udemy.startingpointpersonal.domain

import com.udemy.startingpointpersonal.repository.UserRepository
import javax.inject.Inject

class HomeDomain @Inject constructor(
    private val userRepository: UserRepository
){

    fun logout() {
        //userRepository.clearUserData()
    }

    companion object {
        const val TAG = "HomeDomain"
    }
}