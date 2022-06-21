package com.udemy.startingpointpersonal.api.exceptions

/**
 * Error occurred when the http response is not success, ie, response code <> 2XX
 * @param message raw http response error message
 * @param httpCode http response code
 */
class HttpCodeException(message: String?, val httpCode: Int) : Exception(message)