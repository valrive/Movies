package com.udemy.startingpointpersonal.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udemy.startingpointpersonal.data.database.dao.MovieDao
import com.udemy.startingpointpersonal.data.database.entity.Movie

@Database(
    entities = [
        Movie::class
    ],
    version = 1,
    exportSchema = false)
abstract class LocalRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        const val DB_NAME = "vale.db"
    }
}