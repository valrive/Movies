package com.udemy.startingpointpersonal.di

import com.udemy.startingpointpersonal.data.repository.MovieRepository
import com.udemy.startingpointpersonal.data.repository.MoviesLocalDataSource
import com.udemy.startingpointpersonal.data.repository.MoviesRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Las dependencia definidas aquí vivirán mientras viva el viewmodel
 */

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryVMModule {

    @Provides
    @ViewModelScoped
    fun movieRepositoryProvider(
        moviesLocalDS: MoviesLocalDataSource,
        moviesRemoteDS: MoviesRemoteDataSource
    ) = MovieRepository(moviesLocalDS, moviesRemoteDS)
}