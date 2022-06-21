package com.udemy.startingpointpersonal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udemy.startingpointpersonal.pojos.Jwt

@Dao
interface JwtDao {

    @Query("select * from jwt limit 1")
    fun findToken(): Jwt?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveToken(response: Jwt)

    @Query("delete from jwt")
    fun clear()
}