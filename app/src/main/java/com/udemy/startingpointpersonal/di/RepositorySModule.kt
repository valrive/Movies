package com.udemy.startingpointpersonal.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositorySModule {

    /*@Singleton
    @Provides
    fun userRepositoryProvider(movieDao: MovieDao, api: ApiService) =
        UserRepository(movieDao, api)*/
}