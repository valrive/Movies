package com.udemy.startingpointpersonal.api

import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.udemy.startingpointpersonal.BuildConfig
import com.udemy.startingpointpersonal.api.exceptions.SessionExpiredException
import com.udemy.startingpointpersonal.dao.JwtDao
import com.udemy.startingpointpersonal.pojos.Jwt
import com.udemy.startingpointpersonal.utils.DeviceUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import java.io.IOException
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject

class RefreshTokenInterceptor @Inject constructor(
    private val jwtDao: JwtDao,
    private val application: Application,
    private val apiUrl: String
): Interceptor  {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        // renew token if it's expired
        if (response.code == 401) {
            val tokenExpiredHeader = request.header(authHeader)

            if ( tokenExpiredHeader != null && tokenExpiredHeader.startsWith("Bearer")) {
                val token = updateToken()
                val newRequest = request.newBuilder().header(authHeader, "Bearer $token").build()
                return chain.proceed(newRequest)
            }
        }
        return response
    }

    @Throws(JSONException::class, IOException::class)
    private fun updateToken(): String {
        val url = apiUrl + urlUpdateToken
        val request = Request.Builder()
            .url(url)
            .apply {
                jwtDao.findToken()?.let { jwt ->
                    addHeader("Authorization", "Bearer ${jwt.refreshToken}")
                }
            }
            .header("x-version-name", BuildConfig.VERSION_NAME)
            .header("x-version-id", "${BuildConfig.VERSION_CODE}")
            .header("x-language", Locale.getDefault().language)
            .header("x-terminal",  DeviceUtils.getDeviceId(application))
            .build()

        val response = OkHttpClient().newCall(request).execute()

        if (response.code != 200) { throw SessionExpiredException(response.message)}

        // get ApiBody response
        val jsonResponse = response.body!!.string()

        // convert json to ApiBody object
        val responseType: Type = object : TypeToken<ApiBody<Jwt?>?>() {}.type
        val apiBodyResponse = Gson().fromJson<ApiBody<Jwt>>(jsonResponse, responseType)

        // get JWT
        val jwt = apiBodyResponse.result

        // save new token
        jwtDao.clear()
        jwtDao.saveToken(jwt!!)

        return jwt.token!!
    }

    companion object {
        const val TAG = "TokenInterceptor"
        const val authHeader = "Authorization"
        const val urlUpdateToken = "autorizacion"
    }

}