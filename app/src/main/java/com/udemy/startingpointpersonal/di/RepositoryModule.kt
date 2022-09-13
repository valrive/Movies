package com.udemy.startingpointpersonal.di

import com.udemy.startingpointpersonal.data.repository.MovieRepositoryImpl
import com.udemy.startingpointpersonal.data.repository.interfaces.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
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