package com.udemy.startingpointpersonal.domain

import javax.inject.Inject

class UserDomain @Inject constructor(
){

    fun isLoggedIn() = true//userRepository.isLoggedIn()



    fun clearUserData() {
        //userRepository.clearUserData()
    }

    companion object {
        const val TAG = "UserDomain"
    }
}