package com.udemy.startingpointpersonal.core

import com.udemy.startingpointpersonal.utils.ExceptionParser

sealed class ResourceNew<out T : Any> {

    class Success<out T : Any>(val data: T) : ResourceNew<T>()
    class ErrorSEH(val err: ServiceErrorHandler?) : ResourceNew<Nothing>()
    class ErrorEP(val err: ExceptionParser?) : ResourceNew<Nothing>()

}
//todo: cambiar ExceptionParser.kt a sealed class? https://youtu.be/o-pfaq6tIsI?t=349