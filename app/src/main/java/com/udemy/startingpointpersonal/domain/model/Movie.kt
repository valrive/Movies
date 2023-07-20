package com.udemy.startingpointpersonal.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int = 0,
    val title: String? = "",
    val posterPath: String? = "",
    val backdropPath: String? = "",
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val releaseDate: String? = "",
    val originalLanguage: String? = "",
    val overview: String? = ""
    ): Parcelable

/**
 * Debemos tener 3 modelos de datos para ser lo más independientes posibles del api:
 *
 * En este caso tenemos :
 * Movie para la respuesta del server
 * Movie para guardado en db
 * Movie para usarlo en el dominio de la app
 */