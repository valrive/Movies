package com.udemy.startingpointpersonal.data.api.exceptions

import com.google.android.gms.common.api.ApiException

sealed class ApiExceptionSealed<out T> {

    /**
     * Error occurred when the response body is an instance of ApiBody and success property is false
     * @param errorMmessage
     */
    data class ApiBodyException<out T>(val data: T) : ApiExceptionSealed<Nothing>()

    /**
     * Error occurred when the result field is empty and a non-empty result was expected
     * @param errorMmessage
     */
    data class ResultEmpty<out T>(val data: T) : ApiExceptionSealed<T>()

    /**
     * This is received as an error but must be presented as a warning
     * @param errorMessage
     */
    data class Warning<out T>(val data: T) : ApiExceptionSealed<T>()

    /**
     * Error occurred when the response body is empty and a non-empty body was expected
     * @param message
     */
    data class ApiEmptyBodyException<out T>(val data: T) : ApiExceptionSealed<T>()

    /**
     * Error occurred when the http response is not success, ie, response code <> 2XX
     * @param message raw http response error message
     * @param httpCode http response code
     */
    //data class HttpCodeException(message: String?, val httpCode: Int) : Exception(message)
    data class HttpCodeException<out T>(val data: T) : ApiExceptionSealed<T>()

    /**
     * Error occurred when request could not be processed because session has expired
     * @param message error message
     */
    data class SessionExpired<out T>(val data: T) : ApiExceptionSealed<T>()

}