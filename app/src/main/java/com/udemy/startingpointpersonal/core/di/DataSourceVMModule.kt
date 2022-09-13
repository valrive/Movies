package com.udemy.startingpointpersonal.core.di

import com.udemy.startingpointpersonal.data.api.ApiClient
import com.udemy.startingpointpersonal.data.dao.MovieDao
import com.udemy.startingpointpersonal.data.repository.MoviesLocalDataSourceImpl
import com.udemy.startingpointpersonal.data.repository.MoviesRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

/**
 * Las dependencia definidas aquí vivirán mientras viva el viewmodel
 */

@Module
@InstallIn(ViewModelComponent::class)
class DataSourceVMModule {

    @Provides
    @ViewModelScoped
    fun moviesLocalDataSourceProvider(
        movieDao: MovieDao
    ) = MoviesLocalDataSourceImpl(movieDao)

    @Provides
    @ViewModelScoped
    fun moviesRemoteDataSourceProvider(
        @Named("apiKey") apiKey: String,
        api: ApiClient
    ) = MoviesRemoteDataSourceImpl(apiKey, api)
}