package com.udemy.startingpointpersonal.data.dao

import androidx.room.*

@Dao
interface MovieDao {

    @Query("select 1 from movie")
    fun movieCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg movies: Movie)

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)*/

    @Query("select * from movie")
    fun getAll(): List<Movie>

    @Query("select * from movie where id = :movieId")
    fun findById(movieId: Int): Movie

    @Query("delete from movie")
    fun deleteAll()

    @Delete
    fun deleteMovie(movie: Movie)
}