package com.udemy.startingpointpersonal.di

import com.udemy.startingpointpersonal.data.repository.MovieRepositoryImpl
import com.udemy.startingpointpersonal.data.repository.MoviesLocalDataSourceImpl
import com.udemy.startingpointpersonal.data.repository.MoviesRemoteDataSourceImpl
import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import com.udemy.startingpointpersonal.data.repository.interfaces.MoviesLocalDataSource
import com.udemy.startingpointpersonal.data.repository.interfaces.MoviesRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Las dependencia definidas aquí vivirán mientras viva el viewmodel
 */

@Module
//@InstallIn(ViewModelComponent::class)
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Los módulos provide nos van a servir para proveer
     * dependencias de librerías o clases que contienen interfases
     */

    @Binds
    //@ViewModelScoped
    @Singleton
    abstract fun movieRepositoryProvider(movieRepository: MovieRepositoryImpl): MovieRepository

}