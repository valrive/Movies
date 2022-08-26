package com.udemy.startingpointpersonal.ui.popularMovs

import android.icu.util.TimeZone.getRegion
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.udemy.startingpointpersonal.databinding.FragmentPopularMoviesBinding
import com.udemy.startingpointpersonal.data.pojos.Movie
import com.udemy.startingpointpersonal.ui.*
import com.udemy.startingpointpersonal.ui.popularMovs.adapter.Action
import com.udemy.startingpointpersonal.ui.popularMovs.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@AndroidEntryPoint
class PopularMoviesFragment : BaseFragment<FragmentPopularMoviesBinding>() {

    private val viewModel by viewModels<PopularMoviesViewModel>()
    private val adapter = MovieAdapter { onAction(it) }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when{
                isGranted -> stateFlowCollectors(isGranted)//showFusedLocation(isGranted)//toast("Permission granted")
                shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION) -> activity?.toast("Should show Rationale")
                else -> activity?.toast("Permission denied")
            }
        }

    private fun showFusedLocation(isGranted: Boolean) {
        fusedLocationClient.lastLocation.addOnCompleteListener{
            if(it.result == null){
                //stateFlowCollectors()
                return@addOnCompleteListener
            }

            val geocoder = Geocoder(activity)
            val result = geocoder.getFromLocation(it.result.latitude, it.result.longitude, 1)
            activity?.toast(result.firstOrNull()?.countryCode ?: DEFAULT_REGION)
            //stateFlowCollectors(result.firstOrNull()?.countryCode)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar?.hide()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.rvMovies.adapter = adapter
        binding.rvMovies.layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)

        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)

        //stateFlowCollectors()
    }


    private fun stateFlowCollectors(isGranted: Boolean) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                //todo(Agregar la parte de permisos a la corutina para que se vuelva síncrono con el video #44 de DevExperto)
                activity?.toast("Region = ${getRegion(isGranted)}")
                //todo(Enviar la región al viewmodel)
                viewModel.popularMovies.collect {
                    handleResult(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getPopularMovies.collect {
                    handleResult(it)
                }
            }
        }
    }

    //Tendremos un control asíncrono de lo que queremos que ocurra
    //"continuation" convertirá la parte asíncrona en una parte asíncrona. video #44 de DevExperto
    private suspend fun getRegion(isGranted: Boolean): String = suspendCancellableCoroutine{ continuation ->
        if(!isGranted){
            continuation.resume(DEFAULT_REGION)
            return@suspendCancellableCoroutine
        }

        fusedLocationClient.lastLocation.addOnCompleteListener{
            if(it.result == null){
                continuation.resume(DEFAULT_REGION)
                return@addOnCompleteListener
            }

            val geocoder = Geocoder(activity)
            val result = geocoder.getFromLocation(it.result.latitude, it.result.longitude, 1)
            continuation.resume(result.firstOrNull()?.countryCode ?: DEFAULT_REGION)
        }
    }


    private fun handleResult(state: PopularMoviesViewModel.UiState) {
        when(state.status){
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

    companion object{
        const val DEFAULT_REGION = "US"
    }

}
