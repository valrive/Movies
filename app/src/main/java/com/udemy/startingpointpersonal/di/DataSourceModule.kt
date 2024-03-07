package com.udemy.startingpointpersonal.di

import com.udemy.startingpointpersonal.data.repository.LocalDataSourceImpl
import com.udemy.startingpointpersonal.data.repository.RemoteDataSourceImpl
import com.udemy.startingpointpersonal.data.repository.interfaces.LocalDataSource
import com.udemy.startingpointpersonal.data.repository.interfaces.RemoteDataSource
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
    abstract fun provideLocalDataSource(moviesLocalDS: LocalDataSourceImpl): LocalDataSource

    @Binds
    //@ViewModelScoped
    @Singleton
    abstract fun provideRemoteDataSource(moviesRemoteDS: RemoteDataSourceImpl): RemoteDataSource


}