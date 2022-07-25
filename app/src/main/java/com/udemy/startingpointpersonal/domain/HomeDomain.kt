package com.udemy.startingpointpersonal.domain

import javax.inject.Inject

class HomeDomain @Inject constructor(
){

    fun logout() {
        //userRepository.clearUserData()
    }

    companion object {
        const val TAG = "HomeDomain"
    }
}