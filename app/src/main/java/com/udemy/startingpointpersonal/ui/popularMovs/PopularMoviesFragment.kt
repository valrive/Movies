package com.udemy.startingpointpersonal.ui.popularMovs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.databinding.FragmentPopularMoviesBinding
import com.udemy.startingpointpersonal.pojos.Movie
import com.udemy.startingpointpersonal.presentation.PopularMoviesViewModel
import com.udemy.startingpointpersonal.ui.BaseFragment
import com.udemy.startingpointpersonal.ui.Status
import com.udemy.startingpointpersonal.ui.allMovs.adapters.MoviesAdapter
import com.udemy.startingpointpersonal.ui.allMovs.adapters.concat.PopularConcatAdapter
import com.udemy.startingpointpersonal.ui.login.LoginActivity
import com.udemy.startingpointpersonal.ui.popularMovs.adapter.SingleMovieAdapter
import com.udemy.startingpointpersonal.utils.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment: BaseFragment<FragmentPopularMoviesBinding>(R.layout.fragment_popular_movies),
    SingleMovieAdapter.OnSingleMovieClickListener {
    
    private val movieViewModel by viewModels<PopularMoviesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = movieViewModel
        observers()
    }

    private fun observers(){
        movieViewModel.status.observe(viewLifecycleOwner){ status->
            when(status){
                Status.SUCCESS, Status.FAILURE -> {
                    Util.escondeProgressBar(requireActivity())
                }
                Status.LOADING -> {
                    Util.muestraProgressBar(requireActivity())
                }
                else -> {}
            }
        }

        movieViewModel.getPopularMovies.observe(viewLifecycleOwner){ list->
            binding.rvMovies.adapter = SingleMovieAdapter(
                list.results,
                this@PopularMoviesFragment
            )
        }

        movieViewModel.errorPopularMovies.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it?.mensaje, Toast.LENGTH_SHORT).show()
        }

        movieViewModel.logoutBtn.observe(viewLifecycleOwner) { loggedOut ->
            if (loggedOut) navigateToLogin()
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
        val action =
            PopularMoviesFragmentDirections.actionPopularMoviesFragmentToDetailFragment(movie)
        findNavController().navigate(action)
    }
}
