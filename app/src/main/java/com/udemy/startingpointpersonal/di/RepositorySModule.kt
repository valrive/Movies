package com.udemy.startingpointpersonal.di

import com.udemy.startingpointpersonal.api.ApiService
import com.udemy.startingpointpersonal.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositorySModule {

    /*@Singleton
    @Provides
    fun userRepositoryProvider(movieDao: MovieDao, api: ApiService) =
        UserRepository(movieDao, api)*/
}