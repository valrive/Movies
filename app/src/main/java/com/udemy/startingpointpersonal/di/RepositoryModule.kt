package com.udemy.startingpointpersonal.di

import com.udemy.startingpointpersonal.api.ApiService
import com.udemy.startingpointpersonal.dao.JwtDao
import com.udemy.startingpointpersonal.dao.UserDao
import com.udemy.startingpointpersonal.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun userRepositoryProvider(jwtDao: JwtDao, userDao: UserDao, api: ApiService) =
        UserRepository(jwtDao, userDao, api)
}