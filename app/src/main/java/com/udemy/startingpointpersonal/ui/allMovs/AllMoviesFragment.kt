package com.udemy.startingpointpersonal.ui.allMovs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.core.ApiResult
import com.udemy.startingpointpersonal.databinding.FragmentAllMoviesBinding
import com.udemy.startingpointpersonal.pojos.Movie
import com.udemy.startingpointpersonal.presentation.AllMoviesViewModel
import com.udemy.startingpointpersonal.ui.BaseFragment
import com.udemy.startingpointpersonal.ui.allMovs.adapters.MoviesAdapter
import com.udemy.startingpointpersonal.ui.allMovs.adapters.concat.PopularConcatAdapter
import com.udemy.startingpointpersonal.ui.allMovs.adapters.concat.TopRatedConcatAdapter
import com.udemy.startingpointpersonal.ui.allMovs.adapters.concat.UpcomingConcatAdapter
import com.udemy.startingpointpersonal.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMoviesFragment: BaseFragment<FragmentAllMoviesBinding>(R.layout.fragment_all_movies), MoviesAdapter.OnMovieClickListener {

    private lateinit var concatAdapter: ConcatAdapter

    private val movieViewModel by viewModels<AllMoviesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            viewModel = movieViewModel
        }
        concatAdapter = ConcatAdapter()

        observers()
    }

    private fun observers(){

        movieViewModel.fetchMainScreenMovies().observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    is ApiResult.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is ApiResult.Success -> {
                        progressBar.visibility = View.GONE
                        concatAdapter.apply {
                            addAdapter(
                                0,
                                UpcomingConcatAdapter(
                                    MoviesAdapter(
                                        it.data.first.results,
                                        this@AllMoviesFragment
                                    )
                                )
                            )
                            addAdapter(
                                1,
                                TopRatedConcatAdapter(
                                    MoviesAdapter(
                                        it.data.second.results,
                                        this@AllMoviesFragment
                                    )
                                )
                            )
                            addAdapter(
                                2,
                                PopularConcatAdapter(
                                    MoviesAdapter(
                                        it.data.third.results,
                                        this@AllMoviesFragment
                                    )
                                )
                            )

                        }
                        rvMovies.adapter = concatAdapter
                    }
                    is ApiResult.Failure -> {
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
                is ApiResult.Loading -> {}
                is ApiResult.Success -> navigateToLogin()
                is ApiResult.Failure -> {//showError(it.message!!)
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
        val action = AllMoviesFragmentDirections.actionAllMoviesFragmentToDetailFragment(movie)
        findNavController().navigate(action)
    }
}
