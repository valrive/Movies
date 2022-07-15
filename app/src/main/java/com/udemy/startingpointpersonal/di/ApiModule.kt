package com.udemy.startingpointpersonal.di

import android.app.Application
import com.google.gson.*
import com.udemy.startingpointpersonal.BuildConfig
import com.udemy.startingpointpersonal.api.ApiService
import com.udemy.startingpointpersonal.api.RefreshTokenInterceptor
import com.udemy.startingpointpersonal.dao.JwtDao
import com.udemy.startingpointpersonal.utils.DeviceUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule  {

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().setDateFormat(ApiService.DATE_FORMAT)
        .registerTypeHierarchyAdapter(Date::class.java, object: JsonDeserializer<Date> {

            override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
                return json?.let { Date(it.asLong) }
            }
        })
        .registerTypeHierarchyAdapter(Date::class.java, object: JsonSerializer<Date> {

            override fun serialize(date: Date?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement? {
                return date?.let { JsonPrimitive(it.time) }
            }
        }).create()

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    fun provideHeadersInterceptor(application: Application, jwtDao: JwtDao) = Interceptor {

        it.proceed(
            it.request().newBuilder()
                .apply {
                    // if there's a current user, use its authentication token
                    jwtDao.findToken()?.let { jwt ->
                        addHeader("Authorization", "Bearer ${jwt.token}")
                    }
                }
                .addHeader("x-version-name", BuildConfig.VERSION_NAME)
                .addHeader("x-version-code", "${BuildConfig.VERSION_CODE}")
                .addHeader("x-language", Locale.getDefault().language)
                .addHeader("x-terminal", DeviceUtils.getDeviceId(application))
                //.addHeader("x-terminal", "test")
                .build()
        )
    }

    @Provides
    fun provideTokenInterceptor(application: Application, jwtDao: JwtDao, @Named("apiUrl") apiUrl: String) = RefreshTokenInterceptor(jwtDao, application, apiUrl)

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(headersInterceptor: Interceptor, logging: HttpLoggingInterceptor, refreshTokenInterceptor: RefreshTokenInterceptor): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(headersInterceptor)
        .addInterceptor(refreshTokenInterceptor)
        .addInterceptor(logging)
        .build()

    @Singleton
    @Provides
    fun provideApiService(
        @Named("apiUrl") apiUrl: String,
        converterFactory: GsonConverterFactory,
        httpClient: OkHttpClient
    ): ApiService = Retrofit.Builder()
        .baseUrl(apiUrl)
        .addConverterFactory(converterFactory)
        .client(httpClient)
        .build()
        .create(ApiService::class.java)

}