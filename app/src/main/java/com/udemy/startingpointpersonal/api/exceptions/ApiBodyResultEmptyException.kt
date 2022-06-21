package com.udemy.startingpointpersonal.api.exceptions

/**
 * Error occurred when the result field is empty and a non-empty result was expected
 * @param message
 *
 */
class ApiBodyResultEmptyException(message: String?): Exception(message)