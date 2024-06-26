package com.udemy.startingpointpersonal.data.repository

import com.udemy.startingpointpersonal.data.database.entity.MovieEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieProvider @Inject constructor() {
    var movies: List<MovieEntity> = emptyList()
}