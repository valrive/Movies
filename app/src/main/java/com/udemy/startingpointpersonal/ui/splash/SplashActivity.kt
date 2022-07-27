package com.udemy.startingpointpersonal.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.udemy.startingpointpersonal.ui.MainActivity
import com.udemy.startingpointpersonal.utils.startActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity<MainActivity> {}
            finish()
        }, 2000)
    }

}