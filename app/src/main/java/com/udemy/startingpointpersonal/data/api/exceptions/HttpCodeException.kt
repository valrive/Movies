package com.udemy.startingpointpersonal.data.api.exceptions

class HttpCodeException(message: String?, val httpCode: Int) : Exception(message)