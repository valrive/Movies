package com.udemy.startingpointpersonal.ui.popularMovs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.databinding.FragmentPopularMoviesBinding
import com.udemy.startingpointpersonal.data.pojos.Movie
import com.udemy.startingpointpersonal.ui.*
import com.udemy.startingpointpersonal.ui.popularMovs.adapter.Action
import com.udemy.startingpointpersonal.ui.popularMovs.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesFragment :
    BaseFragment<FragmentPopularMoviesBinding>(R.layout.fragment_popular_movies) {

    private val viewModel by viewModels<PopularMoviesViewModel>()
    private val adapter = MovieAdapter { onAction(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar?.hide()

        binding.rvMovies.adapter = adapter
        binding.rvMovies.layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)

        stateFlowCollectors()
    }

    private fun stateFlowCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state.status) {
                        Status.LOADING -> {
                            binding.loading = true
                            requireActivity().muestraProgressBar()
                        }
                        Status.SUCCESS, Status.FAILURE -> {
                            binding.loading = false
                            requireActivity().escondeProgressBar()
                        }
                    }
                    adapter.submitList(state.movies)

                    /*var newResult = listOf<Movie>()
                    for(movie in state.movies){
                        newResult = listOf(movie) + newResult
                        adapter.submitList(newResult)
                        delay(20)
                    }*/

                }

            }
        }
    }


    private fun onAction(action: Action) {
        when (action) {
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
