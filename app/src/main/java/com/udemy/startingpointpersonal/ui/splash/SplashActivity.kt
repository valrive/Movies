package com.udemy.startingpointpersonal.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.udemy.startingpointpersonal.core.Result
import com.udemy.startingpointpersonal.presentation.SplashViewModel
import com.udemy.startingpointpersonal.ui.MainActivity
import com.udemy.startingpointpersonal.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity: AppCompatActivity() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchLoggedIn().observe(this, Observer {
            when (it) {
                is Result.Loading -> {}

                is Result.Success -> {
                    if( it.data){
                        navigateToHome()
                    } else {
                        navigateToLogin()
                    }
                }
                is Result.Failure -> {
                    Toast.makeText(this, "Error: ${it.exception}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    /**
     * Navigates to main activity and removes splash from the stack
     */
    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    /**
     * Navigates to login activity and removes splash from the stack
     */
    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}