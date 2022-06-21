package com.udemy.startingpointpersonal.core

sealed class Result<out T> {

    //class Loading<out T> : Result<T>()
    object Loading: Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    //data class Failure(val exception: Exception) : Result<Nothing>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
    //data class Failure<out T: Any>(val message: String, val exception: Exception? = null, val value : Any? = null) : Result<T>()
    //data class Failure(val exception: String) : Result<Nothing>()
}
//todo: cambiar ExceptionParser.kt a sealed class? https://youtu.be/o-pfaq6tIsI?t=349