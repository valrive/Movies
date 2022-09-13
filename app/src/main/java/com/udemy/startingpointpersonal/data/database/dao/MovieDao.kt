package com.udemy.startingpointpersonal.data.database.dao

import androidx.room.*
import com.udemy.startingpointpersonal.data.database.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("select 1 from movie_table")
    suspend fun movieCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg movies: MovieEntity)

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)*/

    @Query("select * from movie_table")
    suspend fun getAll(): List<MovieEntity>

    @Query("select * from movie_table where id = :movieId")
    suspend fun findById(movieId: Int): MovieEntity

    @Query("delete from movie_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)
}