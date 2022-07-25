package com.udemy.startingpointpersonal.di

import com.udemy.startingpointpersonal.domain.HomeDomain
import com.udemy.startingpointpersonal.domain.UserDomain
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
    fun userDomainProvider(userRepository: UserRepository): UserDomain = UserDomain(userRepository)

    @Singleton
    @Provides
    fun homeDomainProvider(userRepository: UserRepository): HomeDomain = HomeDomain(userRepository)*/

}