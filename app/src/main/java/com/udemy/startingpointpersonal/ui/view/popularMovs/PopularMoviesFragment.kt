package com.udemy.startingpointpersonal.ui.view.popularMovs

import android.icu.util.TimeZone.getRegion
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
import com.udemy.startingpointpersonal.ui.viewmodel.PopularMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@AndroidEntryPoint
class PopularMoviesFragment: BaseFragment<FragmentPopularMoviesBinding>() {

    private val viewModel: PopularMoviesViewModel by viewModels()
    private var movies: List<Movie> = emptyList()
    private val layoutManager by lazy {
        GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when {
                isGranted -> {
                    setObservers(isGranted)
                    setRVListener()
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
        //ejemplo de adapter de Leiva con DiffUtil
        //MovieAdapter{ onAction(it) }

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

        setBindings()

        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun setBindings() = with(binding){
        lifecycleOwner = this@PopularMoviesFragment
        viewModel = this@PopularMoviesFragment.viewModel

        rvMovies.adapter = adapter
        rvMovies.layoutManager = layoutManager
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
        }
    }


    private fun setObservers(isGranted: Boolean) =
        //El launch lo uso solo para obtener la región
        viewLifecycleOwner.lifecycleScope.launch{
            val region = getRegion(isGranted)

            //LiveData
            //viewModel.moviesLD.observe(viewLifecycleOwner){ handleResult(it) }

            //Flow
            viewModel.moviesF.collect{ handleResult(it) }
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


    private fun <T>handleResult(state: ViewState<T>) {
        binding.shimmer = state is ViewState.Loading
        when (state) {
            is ViewState.Success ->
                (state.data as? List<Movie>)?.let { list: List<Movie> ->
                    adapter.submitList(list)
                }

            is ViewState.Error ->
                activity?.toast(state.message)

            else -> {}

        }
    }

    private fun setRVListener() = viewLifecycleOwner.launchAndCollect(binding.rvMovies.lastVisibleEvents) {
        viewModel.lastVisible.value = it
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

sealed interface ViewState<out T> {
    object Loading: ViewState<Nothing>
    data class Success<T>(val data: T): ViewState<T>
    data class Error(val message: String): ViewState<Nothing>
}
