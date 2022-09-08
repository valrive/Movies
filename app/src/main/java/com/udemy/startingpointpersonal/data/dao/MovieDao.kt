package com.udemy.startingpointpersonal.data.dao

import androidx.room.*

@Dao
interface MovieDao {

    @Query("select 1 from movie")
    suspend fun movieCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg movies: Movie)

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)*/

    @Query("select * from movie")
    suspend fun getAll(): List<Movie>

    @Query("select * from movie where id = :movieId")
    suspend fun findById(movieId: Int): Movie

    @Query("delete from movie")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteMovie(movie: Movie)
}