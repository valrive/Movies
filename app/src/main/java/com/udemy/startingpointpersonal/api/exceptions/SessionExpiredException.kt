package com.udemy.startingpointpersonal.api.exceptions

/**
 * Error occurred when request could not be processed because session has expired
 * @param message error message
 */
class SessionExpiredException(message: String?) : Exception(message)