package com.udemy.startingpointpersonal.di

import android.app.Application
import com.udemy.startingpointpersonal.utils.ExceptionParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExceptionParserModule {

    @Singleton
    @Provides
    fun exceptionParserProvider(context: Application) = ExceptionParser(context)

}