package com.udemy.startingpointpersonal.pojos

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey val id: Int = -1,
    val adult: Boolean = false,
    val backdrop_path: String = "",
    //val genre_ids: List<Int> = listOf(),
    val original_title: String = "",
    val original_language: String = "",
    val overview: String = "",
    val popularity: Double = -1.0,
    val poster_path: String = "",
    val release_date: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = -1.0,
    val vote_count: Int = -1
): Parcelable

data class MovieList(val results: List<Movie> = listOf())