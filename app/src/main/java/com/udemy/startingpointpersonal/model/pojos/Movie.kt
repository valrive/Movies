package com.udemy.startingpointpersonal.model.pojos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(val id: Int, val title: String, val url: String): Parcelable

/**
 * Debemos tener 3 modelos de datos para ser lo más independientes posibles del api:
 *
 * En este caso tenemos :
 * Movie para la respuesta del server
 * Movie para guardado en db
 * Movie para args del Navigation
 */