package com.udemy.startingpointpersonal.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udemy.startingpointpersonal.pojos.User

@Database(
    entities = [
        User::class
    ],
    version = 1,
    exportSchema = false)
abstract class LocalRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun movieDao(): MovieDao

    companion object {

        const val DB_NAME = "vale.db"
    }
}