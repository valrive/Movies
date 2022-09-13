package com.udemy.startingpointpersonal.core.di

import android.app.Application
import com.udemy.startingpointpersonal.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EnvironmentModule {

    @Singleton
    @Provides
    @Named("apiUrl")
    fun provideApiUrl(application: Application) = application.resources.getString(R.string.config_api_url)

    @Singleton
    @Provides
    @Named("apiKey")
    fun provideApiKey(application: Application) = application.resources.getString(R.string.api_key)

    @Singleton
    @Provides
    @Named("environment")
    fun provideEnvironment(application: Application) = application.resources.getString(R.string.config_info_version)

}