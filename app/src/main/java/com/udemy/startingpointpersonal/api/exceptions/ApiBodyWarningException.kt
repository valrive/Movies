package com.udemy.startingpointpersonal.api.exceptions


/**
 * This is received as an error but must be presented as a warning
 * @param message
 *
 */
class ApiBodyWarningException(message: String?): Exception(message)