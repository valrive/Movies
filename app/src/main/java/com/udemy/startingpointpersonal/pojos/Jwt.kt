package com.udemy.startingpointpersonal.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jwt")
data class Jwt(
    @PrimaryKey val id: Long = 1,
    var token: String? = null,
    var refreshToken: String? = null
)
