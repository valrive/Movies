package com.udemy.startingpointpersonal.di

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule  {

    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext context: Context) = PreferenceManager.getDefaultSharedPreferences(context)!!
}