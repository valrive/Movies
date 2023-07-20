package com.udemy.startingpointpersonal.ui.view.popularMovs

import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.databinding.FragmentPopularMoviesBinding
import com.udemy.startingpointpersonal.databinding.MovieItemBinding
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.ui.*
import com.udemy.startingpointpersonal.ui.view.permission.AndroidPermissionChecker
import com.udemy.startingpointpersonal.ui.view.popularMovs.adapter.*
import com.udemy.startingpointpersonal.ui.viewmodel.FlowAristidevsExampleViewModel
import com.udemy.startingpointpersonal.ui.viewmodel.PopularMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@AndroidEntryPoint
class PopularMoviesFragment: BaseFragment<FragmentPopularMoviesBinding>() {

    private val viewModel: PopularMoviesViewModel by viewModels()
    private val aristiViewModel by viewModels<FlowAristidevsExampleViewModel>()
    private var movies: List<Movie> = emptyList()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when {
                isGranted -> {
                    stateFlowCollectors(isGranted, false)
                    //showFusedLocation(isGranted)//toast("Permission granted")
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                    activity?.toast( "Should show Rationale" )
                }
                else -> activity?.toast("Permission denied")
            }
        }

    /** Adapters */
    private val adapter =
        MovieAdapterLA { onAction(it) }

        /*GLAdapterDB(
            inflate = {li, parent, attachToParent ->
                MovieItemBinding.inflate(li, parent, attachToParent)
            },
            onBind = { binding, movie: Movie, _ ->
                bindMovie(binding, movie)
            },
            onAction = {
                onAction(it)
            }
        )*/
        /*GLAdapterL<Movie>(
            layoutId = R.layout.movie_item,
            //bind = { item, _, _, binding -> bindMovie(binding as MovieItemBinding, item) },
            onAction = { onAction(it) }
        )*/


        //Adapter básico optimizado con basicDiffUtil, ya no lleva el list como parámetro
        //MovieAdapter { onAction(it) }

        //ejemplo de adapter de Aristi con DiffUtil
        //MovieAdapterAristi(movies) { onAction(it) }

        //ejemplo de adapter de Leiva con DiffUtil
        //MovieAdapter{ onAction(it) }



    //sin validar

    private val GRVAdapterL = GRVAdapterL(
        movies,
        R.layout.movie_item
    ){ item, binding ->
        bindMovie(binding as MovieItemBinding, item)
    }
    private val GRVAdapterDB = GRVAdapterDB(
        data = emptyList<Movie>(),
        onBind = {binding, movie, _ ->
            bindMovie(binding , movie)
        },
        inflate = {li, parent, attachToParent ->
            MovieItemBinding.inflate(li, parent, attachToParent)
        }
    )

    private val gLAdapterDB = GLAdapterDB(
        inflate = {li, parent, attachToParent ->
            MovieItemBinding.inflate(li, parent, attachToParent)
        },
        onBind = { binding, movie: Movie, _ ->
            bindMovie(binding, movie)
        },
        onAction = { onAction(it) }
    )

    private val bindingInterface = object:
        GRVAdapterL.GenericRecyclerAdapterBindingInterface<Movie> {
        override fun bindData(item: Movie, binding: ViewDataBinding) {
            TODO("Not yet implemented")
        }
    }

    /** Adapters */





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AndroidPermissionChecker(requireActivity()).checkPermissions()

        (requireActivity() as MainActivity).supportActionBar?.hide()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.rvMovies.adapter = adapter
        binding.rvMovies.layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)

        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)

        //stateFlowCollectors()

        //Aristidev example
        viewLifecycleOwner.launchAndCollect(aristiViewModel.uiState){
            handleResult(it)
        }
        aristiViewModel.example()
    }

    private fun showFusedLocation(isGranted: Boolean) {
        fusedLocationClient.lastLocation.addOnCompleteListener {
            if (it.result == null) {
                //stateFlowCollectors()
                return@addOnCompleteListener
            }

            val geocoder = Geocoder(requireContext())
            val result = geocoder.getFromLocation(it.result.latitude, it.result.longitude, 1)
            activity?.toast(result?.firstOrNull()?.countryCode ?: DEFAULT_REGION)
            //stateFlowCollectors(result.firstOrNull()?.countryCode)
        }
    }


    private fun stateFlowCollectors(isGranted: Boolean, liveDataMode: Boolean) =
        //El launch lo uso solo para obtener la región
        viewLifecycleOwner.lifecycleScope.launch{
            val region = getRegion(isGranted)

            //3 modos de llamar a los livedata y state flow
            if (liveDataMode) {

                //viewModel.popularMoviesLD.observe(viewLifecycleOwner, Observer{handleResult(it)})



                //primer modo de llamado (fun en viewModel + listener apartados como en banortec)
                /*viewModel.initGetPopularMovies()
                viewModel.getPopularMoviesLD.observe(viewLifecycleOwner, Observer{handleResult(it)})
                */



                //segundo modo de llamado; Llamando directamente a un fun y no depender de observar una var y llamar a una fun
                viewModel.fetchPopularMoviesLD(region).observe(viewLifecycleOwner, Observer{
                    handleResult(it)}
                )

            } else { //flow mode

                viewLifecycleOwner.launchAndCollect(viewModel.popularMoviesF){
                    handleResult(it)
                }

                //primer modo de llamado (fun en viewModel + listener apartados como en banortec)
                /*viewModel.initGetPopularMovies()
                viewLifecycleOwner.launchAndCollect(viewModel.getPopularMoviesSF){
                    handleResult(it)
                }*/

                //segundo modo de llamado; Llamando directamente a un fun y no depender de observar una var y llamar a una fun
                /*viewLifecycleOwner.launchAndCollect(viewModel.fetchPopularMoviesSF(region)){
                    handleResult(it)
                }*/
                //opción vieja
                /*viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        //todo(Agregar la parte de permisos a la corutina para que se vuelva síncrono con el video #44 de DevExperto)
                        viewModel.fetchPopularMoviesFlow(getRegion(isGranted)).collect {
                            handleResult(it)
                        }
                    }
                }*/
            }
        }


    //Tendremos un control asíncrono de lo que queremos que ocurra
    //"continuation" convertirá la parte asíncrona en una parte asíncrona. video #44 de DevExperto
    private suspend fun getRegion(isGranted: Boolean): String =
        suspendCancellableCoroutine { continuation ->
            if (!isGranted) {
                continuation.resume(DEFAULT_REGION)
                return@suspendCancellableCoroutine
            }

            fusedLocationClient.lastLocation.addOnCompleteListener {
                if (it.result == null) {
                    continuation.resume(DEFAULT_REGION)
                    return@addOnCompleteListener
                }

                val geocoder = Geocoder(requireContext())
                val result = geocoder.getFromLocation(it.result.latitude, it.result.longitude, 1)
                continuation.resume(result?.firstOrNull()?.countryCode ?: DEFAULT_REGION)
            }
        }


    private fun handleResult(state: PopularMoviesUIState<List<Movie>>) {
        when (state) {
            PopularMoviesUIState.Loading -> {
                binding.loading = true
                requireActivity().muestraProgressBar()
            }
            is PopularMoviesUIState.Success -> {
                binding.loading = false
                requireActivity().escondeProgressBar()

                //el método submitList() extiende solo de ListAdapter, si queremos que funcione
                // para ambos (ListAdapter y ReciclerView.Adapter) entonces al ReciclerView.Adapter
                // hay que agregarle una función con el mismo nombre
                adapter.submitList(state.list)
            }
            is PopularMoviesUIState.Error -> {
                binding.loading = false
                requireActivity().escondeProgressBar()
                activity?.toast(state.mensaje)
            }
        }
    }


    private fun onAction(action: Action) {
        when (action) {
            is Action.Click -> navigateToDetail(action.item as Movie)
            is Action.Delete -> TODO()
            is Action.Favorite -> TODO()
            is Action.Share -> TODO()
        }
    }

    private fun bindMovie(binding: MovieItemBinding, movie: Movie) = with(binding){
        url = movie.posterPath
        title = movie.title
    }

    private fun navigateToDetail(movie: Movie) = findNavController().navigate(
        PopularMoviesFragmentDirections.actionPopularMoviesFragmentToDetailFragment(movie)
    )

    companion object {
        const val DEFAULT_REGION = "US"
    }

}

sealed class PopularMoviesUIState<out T> {
    object Loading: PopularMoviesUIState<Nothing>()
    data class Success<out T>(val list: T): PopularMoviesUIState<T>()
    data class Error(val mensaje: String): PopularMoviesUIState<Nothing>()
}

sealed class PopularMoviesUIState2{
    object Loading: PopularMoviesUIState2()
    data class Success(val list: Movie): PopularMoviesUIState2()
    data class Error(val mensaje: String): PopularMoviesUIState2()
}
