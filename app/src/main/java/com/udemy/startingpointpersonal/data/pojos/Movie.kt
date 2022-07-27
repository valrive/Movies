package com.udemy.startingpointpersonal.data.pojos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val originalLanguage: String,
    val overview: String
    ): Parcelable

/**
 * Debemos tener 3 modelos de datos para ser lo más independientes posibles del api:
 *
 * En este caso tenemos :
 * Movie para la respuesta del server
 * Movie para guardado en db
 * Movie para args del Navigation
 */