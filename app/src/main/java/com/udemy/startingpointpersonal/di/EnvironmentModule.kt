package com.udemy.startingpointpersonal.di

import android.content.Context
import com.udemy.startingpointpersonal.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EnvironmentModule {

    @Singleton
    @Provides
    @Named("apiUrl")
    fun provideApiUrl(@ApplicationContext context: Context) = context.resources.getString(R.string.config_api_url)

    @Singleton
    @Provides
    @Named("apiKey")
    fun provideApiKey(@ApplicationContext context: Context) = context.resources.getString(R.string.api_key)

    @Singleton
    @Provides
    @Named("environment")
    fun provideEnvironment(@ApplicationContext context: Context) = context.resources.getString(R.string.config_info_version)

}