package com.udemy.startingpointpersonal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udemy.startingpointpersonal.pojos.User

@Dao
interface MovieDao {

    @Query("select * from user limit 1")
    fun findCurrent(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(user: User)

    @Query("delete from user")
    fun deleteAll()
}