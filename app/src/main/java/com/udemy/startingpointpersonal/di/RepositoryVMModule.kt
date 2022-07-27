package com.udemy.startingpointpersonal.di

import com.udemy.startingpointpersonal.model.api.ApiService
import com.udemy.startingpointpersonal.model.dao.MovieDao
import com.udemy.startingpointpersonal.model.repository.MovieRepository
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
class RepositoryVMModule {

    @Provides
    @ViewModelScoped
    fun movieRepositoryProvider(
        @Named("apiKey") apiKey: String,
        movieDao: MovieDao,
        api: ApiService
    ) = MovieRepository(apiKey, movieDao, api)
}