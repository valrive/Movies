package com.udemy.startingpointpersonal.data.api.wrapper

import com.udemy.startingpointpersonal.data.api.exceptions.ApiEmptyBodyException
import com.udemy.startingpointpersonal.data.api.exceptions.HttpCodeException
import retrofit2.Call

object ApiWrapper {

    /**
     * Generic function for requests with nullable response body, reduces boilerplate id.
     * @param call the call for API
     * @return
     */
    private fun <T> create(call: Call<T>): T? {
        val response = call.execute()

        if (!response.isSuccessful) {
            throw HttpCodeException("HttpCodeException ", response.code())
        }

        // success
        return response.body()
    }

    /**
     * Generic function for requests with response body, reduces boilerplate id.
     * @param call the call for API
     * @return
     */
    fun <T> createForRequiredBody(call: Call<T>): T {
        // success
        return create(call) ?: throw ApiEmptyBodyException("Response body is null")
    }


}

