package com.udemy.startingpointpersonal.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udemy.startingpointpersonal.utils.annotation.Required

/**
 * Created by Jorge Segundo on 2019-07-30.
 *
 * id is final with a default value cuz we want a single user
 */
@Entity(tableName = "user")
data class User (
        @PrimaryKey val id: Long = 1,
        @Required var username: String? = null,
        @Required var password: String? = null,
        var nombre: String? = null,
        var roleId: String? = null,
        var roleDescription: String? = null,
        var empresaId: Long? = null,
        var areaId: Long? = null,
        var isAdminSecuenciado: Boolean? = null
)