package com.udemy.startingpointpersonal.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udemy.startingpointpersonal.data.database.dao.MovieDao
import com.udemy.startingpointpersonal.data.database.entity.MovieEntity

@Database(
    entities = [
        MovieEntity::class
    ],
    version = 1,
    exportSchema = false)
abstract class LocalRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}