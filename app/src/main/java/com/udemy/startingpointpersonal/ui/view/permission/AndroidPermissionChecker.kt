package com.udemy.startingpointpersonal.ui.view.permission

import android.Manifest.permission.*
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class AndroidPermissionChecker(private val activity: Activity) : PermissionManager {

    private val RECORD_COARSE_CODE = 101

    override fun checkPermissions() {
        setupPermissions()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            activity,
            ACCESS_COARSE_LOCATION
        )

        if(permission != PackageManager.PERMISSION_GRANTED){
            Log.i("AndroidPermChecker", "Permission to record denied")
            makeRequest()
        }
    }

    private fun makeRequest() {
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