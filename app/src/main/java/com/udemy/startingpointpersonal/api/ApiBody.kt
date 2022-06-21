package com.udemy.startingpointpersonal.api


//data class ApiBody<T>(val success: Boolean, val message: String, val result: T)
  data class ApiBody<T>(val success: Boolean, val message: String, val result: T?, val code: Int? = null) {

    companion object {

        const val CODE_WARNING = -20002
    }
}