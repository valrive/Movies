package com.udemy.startingpointpersonal.utils

class RequiredFieldException(
    message: String?,
    val fieldsRequired: List<String>
) : Exception(message)
