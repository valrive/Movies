package com.udemy.startingpointpersonal.api.exceptions

/**
 * Error occurred when the response body is empty and a non-empty body was expected
 * @param message
 *
 */
class ApiEmptyBodyException(message: String?) : Exception(message)