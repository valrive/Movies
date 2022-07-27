package com.udemy.startingpointpersonal.di

import com.udemy.startingpointpersonal.data.repository.MovieRepository
import com.udemy.startingpointpersonal.domain.GetAllMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Singleton
    @Provides
    fun GetAllMoviesUseCaseProvider(repo: MovieRepository) = GetAllMoviesUseCase(repo)

}