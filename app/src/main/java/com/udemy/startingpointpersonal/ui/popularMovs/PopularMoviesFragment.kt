package com.udemy.startingpointpersonal.ui.popularMovs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.databinding.FragmentPopularMoviesBinding
import com.udemy.startingpointpersonal.pojos.Movie
import com.udemy.startingpointpersonal.presentation.PopularMoviesViewModel
import com.udemy.startingpointpersonal.ui.BaseFragment
import com.udemy.startingpointpersonal.ui.Status
import com.udemy.startingpointpersonal.ui.popularMovs.adapter.Action
import com.udemy.startingpointpersonal.ui.popularMovs.adapter.SingleMovieAdapter
import com.udemy.startingpointpersonal.utils.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment: BaseFragment<FragmentPopularMoviesBinding>(R.layout.fragment_popular_movies) {
    
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
            SingleMovieAdapter(list.results){
                onAction(it)
            }.also { binding.rvMovies.adapter = it }
        }

        movieViewModel.errorPopularMovies.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it?.mensaje, Toast.LENGTH_SHORT).show()
        }

    }

    private fun onAction(action: Action) {
        when(action){
            is Action.Click -> navigateToDetail(action.movie)
            is Action.Delete -> TODO()
            is Action.Favorite -> TODO()
            is Action.Share -> TODO()
        }
    }

    private fun navigateToDetail(movie: Movie) = findNavController().navigate(
            PopularMoviesFragmentDirections.actionPopularMoviesFragmentToDetailFragment(movie)
    )

}
