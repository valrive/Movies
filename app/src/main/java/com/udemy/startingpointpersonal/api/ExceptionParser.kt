package com.udemy.startingpointpersonal.api

import android.content.Context
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.api.exceptions.*
import com.udemy.startingpointpersonal.utils.RequiredFieldException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * Created by Valentin Rivera on 2021/06/04.
 */
class ExceptionParser @Inject constructor(
    private val context: Context
){

    fun toPresentableMessage(throwable: Throwable): Exception = when (throwable) {

        is RequiredFieldException -> getRequiredFieldExceptionMessage(throwable)

        is HttpCodeException -> getHttpErrorMessage(throwable)

        is ApiBodyException -> getApiBodyException(throwable)

        is ApiEmptyBodyException -> getApiBodyEmptyException(throwable)

        is ApiBodyWarningException -> ApiBodyWarningException(throwable.message)

        is SocketTimeoutException -> getApiTimeoutExceptionMessage(throwable)

        is ConnectException -> getApiConnectExceptionMessage(throwable)

        is SessionExpiredException -> getSessionExpiredExceptionMessage(throwable)

        else -> Exception(throwable.fillInStackTrace().toString())

    } ?: Exception(context.getString(R.string.error_unknown, throwable.fillInStackTrace().toString()))

    /**
     * The explicit error message is in the response body
     *
     * @param e: exception
     * @return error message
     */
    private fun getRequiredFieldExceptionMessage(e: RequiredFieldException): Exception {
        return RequiredFieldException(
            "Los campos ${e.fieldsRequired} no pueden estar vacíos",
            e.fieldsRequired
        )
    }

    /**
     * Message for the most common http response codes
     *
     * @param e: exception
     * @return error message
     */
    private fun getHttpErrorMessage(e: HttpCodeException) = HttpCodeException(
        when (e.httpCode) {
            404 -> context.getString(R.string.error_http_404)
            401 -> context.getString(R.string.error_http_401)
            403 -> context.getString(R.string.error_http_403)
            408 -> context.getString(R.string.error_http_408)
            500 -> context.getString(R.string.error_http_500)
            else -> context.getString(R.string.error_http_code, e.httpCode)
        },
        e.httpCode
    )


    /**
     * The explicit error message is in the response body
     *
     * @param e: exception
     * @return error message
     */
    private fun getApiBodyException(e: ApiBodyException) = ApiBodyException(e.message)

    /**
     * When response body was expected but get an empty body instead
     *
     * @param e: exception
     * @return error message
     */
    private fun getApiBodyEmptyException(e: ApiEmptyBodyException) =
        ApiEmptyBodyException(context.getString(R.string.error_http_empty_body))

    /**
    +     * When server times out
    +     *
    +     * @param t: throwable
    +     * @return error message
    +     */
    private fun getApiTimeoutExceptionMessage(t: Throwable): Exception {
        return SocketTimeoutException(context.getString(R.string.error_http_408))
    }

    /**
     * When could't establish connection
     *
     * @param t: throwable
     * @return error message
     */
    private fun getApiConnectExceptionMessage(t: Throwable): Exception {
        return ConnectException(context.getString(R.string.error_verify_internet_connection))
    }

    /**
     * When session has been expired
     *
     * @param t: throwable
     * @return error message
     */
    private fun getSessionExpiredExceptionMessage(t: Throwable): Exception {
        return SessionExpiredException(context.getString(R.string.info_dialog_expired_session))
    }
}