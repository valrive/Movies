package com.udemy.startingpointpersonal.data.api.exceptions

class HttpCodeException(message: String?, httpCode: Int) : Exception("$message $httpCode")