package com.udemy.startingpointpersonal.di

import com.udemy.startingpointpersonal.data.repository.MoviesLocalDataSourceImpl
import com.udemy.startingpointpersonal.data.repository.interfaces.MoviesLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositorySModule {

    /*@Singleton
    @Provides
    fun userRepositoryProvider(movieDao: MovieDao, api: ApiService) =
        UserRepository(movieDao, api)*/

}