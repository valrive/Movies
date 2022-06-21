package com.udemy.startingpointpersonal.api

import com.udemy.startingpointpersonal.api.exceptions.ApiBodyException
import com.udemy.startingpointpersonal.api.exceptions.ApiBodyWarningException
import com.udemy.startingpointpersonal.api.exceptions.ApiEmptyBodyException
import com.udemy.startingpointpersonal.api.exceptions.HttpCodeException
import retrofit2.Call

/**
 * Created by Valentin on 2019-12-19.
 */
object ApiResults {

    /**
     * Generic function for requests with response body, reduces boilerplate id.
     * @param call the call for API
     * @return
     */
    fun <T> createForRequiredBody(call: Call<T>): T {
        // success
        return create(call) ?: throw ApiEmptyBodyException("Response body is null")
    }

    /**
     * Generic function for requests with nullable response body, reduces boilerplate id.
     * @param call the call for API
     * @return
     */
    fun <T> create(call: Call<T>): T? {
        val response = call.execute()

        if (!response.isSuccessful) {
            throw HttpCodeException(response.message(), response.code())
        }

        // success
        return response.body()
    }

    /**
     * Generic function for requests without ApiBody as response body, reduces boilerplate id.
     * @param call the call for API
     * @return
     */
    fun <T> createForNonApiBody(call: Call<T>): T{
        val response = call.execute()

        if (!response.isSuccessful) {
            throw HttpCodeException(response.message(), response.code())
        }

        // success
        return response.body() ?: throw ApiEmptyBodyException("Response body is null")
    }

    /**
     * Generic function for requests with ApiBody as response body, reduces boilerplate id.
     * @param call the call for API
     * @return
     */
    fun <T> createForApiBody(call: Call<ApiBody<T>>): T?{
        val response = call.execute()

        if (!response.isSuccessful) {
            throw HttpCodeException(response.message(), response.code())
        }

        val body = response.body() ?: throw ApiEmptyBodyException("Response body is null")

        if (body.code == ApiBody.CODE_WARNING) {
            throw ApiBodyWarningException(body.message)
        }

        if (!body.success) {
            throw ApiBodyException(body.message)
        }

        // success
        return body.result
    }

    /**
     * Generic function for requests with a list as response body, reduces boilerplate id.
     * @param call the call for API
     * @return the response's MutableList
     */
    fun <T> createForList(call: Call<List<T>>): List<T> {
        val response = call.execute()

        if (!response.isSuccessful) {
            throw HttpCodeException(response.message(), response.code())
        }

        /**
         * occurred when the response body is an instance of ApiBody and success property is false
         */
        //ApiBodyException

        return response.body() ?: throw ApiEmptyBodyException("Response body is null")
    }


}