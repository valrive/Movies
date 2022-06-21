package com.udemy.startingpointpersonal.ui.home

import com.udemy.startingpointpersonal.api.ApiService
import com.udemy.startingpointpersonal.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

/**
 * Las dependencia definidas aquí vivirán mientras viva el viewmodel
 */

@Module
@InstallIn(ViewModelComponent::class)
class Homemodule {

    @Provides
    @ViewModelScoped
    fun movieRepositoryProvider(@Named("apiKey") apiKey: String, api: ApiService) =
        MovieRepository(apiKey, api)
}