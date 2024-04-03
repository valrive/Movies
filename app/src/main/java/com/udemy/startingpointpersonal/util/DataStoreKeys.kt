package com.udemy.startingpointpersonal.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val movieStringKey = stringPreferencesKey("movie_string_key")
    val sampleKey = intPreferencesKey("sampleIntPreferenceKey")
    val isVerifiedEmailKey = booleanPreferencesKey("verified_email")
    val isAnonymousKey = booleanPreferencesKey("user_anonymous")
}