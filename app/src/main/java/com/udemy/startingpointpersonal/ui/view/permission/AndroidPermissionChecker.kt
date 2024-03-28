package com.udemy.startingpointpersonal.ui.view.permission

import android.Manifest.permission.*
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class AndroidPermissionChecker(private val activity: Activity) : PermissionManager {

    companion object {
        private const val RECORD_COARSE_CODE = 101
    }

    override fun checkPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            activity,
            ACCESS_COARSE_LOCATION
        )

        if(permission != PackageManager.PERMISSION_GRANTED){
            Log.i("AndroidPermChecker", "Permission to record denied")

            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    ACCESS_COARSE_LOCATION,
                    //Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                RECORD_COARSE_CODE
            )

        }


    }



}