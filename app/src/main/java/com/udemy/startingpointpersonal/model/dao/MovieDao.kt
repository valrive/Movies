package com.udemy.startingpointpersonal.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udemy.startingpointpersonal.model.pojos.Movie

@Dao
interface MovieDao {

    @Query("select * from movie limit 1")
    fun findCurrent(): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(user: Movie)

    @Query("delete from movie")
    fun deleteAll()
}