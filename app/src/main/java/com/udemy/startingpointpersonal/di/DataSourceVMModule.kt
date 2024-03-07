package com.udemy.startingpointpersonal.di

import com.udemy.startingpointpersonal.data.api.ApiClient
import com.udemy.startingpointpersonal.data.database.dao.MovieDao
import com.udemy.startingpointpersonal.data.repository.LocalDataSourceImpl
import com.udemy.startingpointpersonal.data.repository.RemoteDataSourceImpl
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
    ) = LocalDataSourceImpl(movieDao)

    @Provides
    @ViewModelScoped
    fun moviesRemoteDataSourceProvider(
        @Named("apiKey") apiKey: String,
        api: ApiClient
    ) = RemoteDataSourceImpl(apiKey, api)
}