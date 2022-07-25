package com.udemy.startingpointpersonal.di

import android.app.Application
import androidx.room.Room
import com.udemy.startingpointpersonal.dao.LocalRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = Room
        .databaseBuilder(application, LocalRoomDatabase::class.java,LocalRoomDatabase.DB_NAME)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideMovieDao(database: LocalRoomDatabase) = database.movieDao()


}