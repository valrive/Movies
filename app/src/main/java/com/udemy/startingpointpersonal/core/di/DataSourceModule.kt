package com.udemy.startingpointpersonal.core.di

import com.udemy.startingpointpersonal.data.repository.MoviesLocalDataSourceImpl
import com.udemy.startingpointpersonal.data.repository.MoviesRemoteDataSourceImpl
import com.udemy.startingpointpersonal.data.repository.interfaces.MoviesLocalDataSource
import com.udemy.startingpointpersonal.data.repository.interfaces.MoviesRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
//@InstallIn(ViewModelComponent::class)
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    /**
     * Los módulos provide nos van a servir para proveer
     * dependencias de librerías o clases que contienen interfases
     */

    @Binds
    //@ViewModelScoped
    @Singleton
    abstract fun provideMoviesLocalDataSource(moviesLocalDS: MoviesLocalDataSourceImpl): MoviesLocalDataSource

    @Binds
    //@ViewModelScoped
    @Singleton
    abstract fun provideMoviesRemoteDataSource(moviesRemoteDS: MoviesRemoteDataSourceImpl): MoviesRemoteDataSource


}