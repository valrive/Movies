package com.udemy.startingpointpersonal.di

import com.udemy.startingpointpersonal.data.repository.MovieRepositoryImpl
import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Las dependencia definidas aquí vivirán mientras viva el viewmodel
 */

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryVMModule {

    /*@Provides
    @ViewModelScoped
    fun movieRepositoryProvider(
        moviesLocalDS: MoviesLocalDataSource,
        moviesRemoteDS: MoviesRemoteDataSource
    ) = MovieRepository(moviesLocalDS, moviesRemoteDS)*/

    @Binds
    @ViewModelScoped
    abstract fun movieRepositoryProvider(movieRepository: MovieRepositoryImpl): MovieRepository
}