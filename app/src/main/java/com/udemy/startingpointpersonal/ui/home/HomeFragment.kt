package com.udemy.startingpointpersonal.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.core.Result
import com.udemy.startingpointpersonal.databinding.FragmentHomeBinding
import com.udemy.startingpointpersonal.pojos.Movie
import com.udemy.startingpointpersonal.presentation.HomeViewModel
import com.udemy.startingpointpersonal.ui.BaseFragment
import com.udemy.startingpointpersonal.ui.home.adapters.MoviesAdapter
import com.udemy.startingpointpersonal.ui.home.adapters.concat.PopularConcatAdapter
import com.udemy.startingpointpersonal.ui.home.adapters.concat.TopRatedConcatAdapter
import com.udemy.startingpointpersonal.ui.home.adapters.concat.UpcomingConcatAdapter
import com.udemy.startingpointpersonal.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), MoviesAdapter.OnMovieClickListener {

    private lateinit var concatAdapter: ConcatAdapter

    private val movieViewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //_binding = FragmentHomeBinding.bind(view)

        with(binding){
            viewModel = movieViewModel
        }

        concatAdapter = ConcatAdapter()

        movieViewModel.fetchMainScreenMovies().observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    is Result.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        progressBar.visibility = View.GONE
                        concatAdapter.apply {
                            addAdapter(
                                0,
                                UpcomingConcatAdapter(
                                    MoviesAdapter(
                                        it.data.first.results,
                                        this@HomeFragment
                                    )
                                )
                            )
                            addAdapter(
                                1,
                                TopRatedConcatAdapter(
                                    MoviesAdapter(
                                        it.data.second.results,
                                        this@HomeFragment
                                    )
                                )
                            )
                            addAdapter(
                                2,
                                PopularConcatAdapter(
                                    MoviesAdapter(
                                        it.data.third.results,
                                        this@HomeFragment
                                    )
                                )
                            )

                        }
                        rvMovies.adapter = concatAdapter
                    }
                    is Result.Failure -> {
                        progressBar.visibility = View.GONE
                        Log.e("FetchError", "Error: $it.exception ")
                        Toast.makeText(
                            requireContext(),
                            "Error: ${it.exception}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }

        }

        movieViewModel.logoutBtn.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {}
                is Result.Success -> navigateToLogin()
                is Result.Failure -> {//showError(it.message!!)
                }
            }
        }

    }

    /**
     * Navigates to login activity and finish current activity
     */
    private fun navigateToLogin() {
        startActivity(Intent(context, LoginActivity::class.java))
        activity?.finish()
    }

    override fun onMovieClick(movie: Movie) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie)
        findNavController().navigate(action)
    }
}
