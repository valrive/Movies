package com.udemy.startingpointpersonal.api.exceptions

/**
 * Error occurred when the response body is an instance of ApiBody and success property is false
 * @param message
 */

class ApiBodyException(message: String?) : Exception(message)