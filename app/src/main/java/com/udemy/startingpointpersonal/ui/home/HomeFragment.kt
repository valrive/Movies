package com.udemy.startingpointpersonal.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.databinding.FragmentHomeBinding
import com.udemy.startingpointpersonal.ui.BaseFragment
import com.udemy.startingpointpersonal.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            btnAllMovies.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAllMoviesFragment())
            }

            btnPopularMovies.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPopularMoviesFragment())
            }

            btnLogout.setOnClickListener {
                startActivity(Intent(context, LoginActivity::class.java))
                activity?.finish()
            }
        }
    }

}
