package com.udemy.startingpointpersonal.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Movie::class
    ],
    version = 2,
    exportSchema = false)
abstract class LocalRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        const val DB_NAME = "vale.db"
    }
}