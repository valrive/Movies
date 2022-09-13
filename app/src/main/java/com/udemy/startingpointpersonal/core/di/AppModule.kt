package com.udemy.startingpointpersonal.core.di

import android.app.Application
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule  {

    @Provides
    @Singleton
    fun providePreferences(application: Application) = PreferenceManager.getDefaultSharedPreferences(application)!!
}