package com.udemy.startingpointpersonal.di

import com.udemy.startingpointpersonal.domain.GetAllMoviesUseCase
import com.udemy.startingpointpersonal.domain.model.interfaces.GeneralUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun provideGetAllMoviesUseCase(getAllMoviesUseCase: GetAllMoviesUseCase) : GeneralUseCase


}