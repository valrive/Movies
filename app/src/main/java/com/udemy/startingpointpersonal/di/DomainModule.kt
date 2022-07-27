package com.udemy.startingpointpersonal.di

import com.udemy.startingpointpersonal.domain.HomeDomain
import com.udemy.startingpointpersonal.model.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    /*@Singleton
    @Provides
    fun userDomainProvider(userRepository: UserRepository): UserDomain = UserDomain(userRepository)*/

    @Singleton
    @Provides
    fun homeDomainProvider(repo: MovieRepository) = HomeDomain(repo)

}