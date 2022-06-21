package com.udemy.startingpointpersonal.utils

import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import android.provider.Settings
import com.udemy.startingpointpersonal.R

object DeviceUtils {

    /**
     * Get device id, first not null duid: mac, device traza, andorid id, current millis
     * @return Device Id
     */
    fun getDeviceId(context: Context): String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val duidKey = context.getString(R.string.config_duid)
        var duid = preferences.getString(duidKey, null)

        // set duid, first not null duid: mac, device serial, andorid id, current millis
        if (duid == null) {
            // mac
            duid = WifiUtils.getMACAddress(context)

            // traza
            if (duid == null || duid.trim { it <= ' ' }.isEmpty()) {
                duid = Build.SERIAL
                duid = if (duid == null || duid.trim { it <= ' ' }.isEmpty()) null else "S_$duid"
            }

            // android id
            if (duid == null || duid.trim { it <= ' ' }.isEmpty()) {
                duid = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
                duid = if (duid == null || duid.trim { it <= ' ' }.isEmpty()) null else "A_$duid"
            }

            // current millis
            if (duid == null || duid.trim { it <= ' ' }.isEmpty()) {
                duid = "T_" + System.currentTimeMillis()
            }

            val editor = preferences.edit()
            editor.putString(duidKey, duid)
            editor.apply()
        }

        return duid
    }

}
