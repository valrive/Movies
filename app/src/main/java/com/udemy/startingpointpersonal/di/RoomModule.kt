package com.udemy.startingpointpersonal.di

import android.content.Context
import androidx.room.Room
import com.udemy.startingpointpersonal.data.database.LocalRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DB_NAME = "vale.db"

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, LocalRoomDatabase::class.java, DB_NAME)
        //.allowMainThreadQueries()
        //.fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideMovieDao(database: LocalRoomDatabase) = database.movieDao()


}