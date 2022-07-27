package com.udemy.startingpointpersonal.data.api

sealed class ApiResult<out T> {

    //class Loading<out T> : Result<T>()
    //object Loading: ApiResult<Nothing>()
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Failure(val exception: Throwable) : ApiResult<Nothing>()
    //data class Failure(val exception: Exception) : Result<Nothing>()
    //data class Failure<out T: Any>(val message: String, val exception: Exception? = null, val value : Any? = null) : Result<T>()
    //data class Failure(val exception: String) : Result<Nothing>()
}
//todo: cambiar ExceptionParser.kt a sealed class? https://youtu.be/o-pfaq6tIsI?t=349