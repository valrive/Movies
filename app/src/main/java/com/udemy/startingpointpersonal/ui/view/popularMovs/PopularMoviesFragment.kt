package com.udemy.startingpointpersonal.ui.view.popularMovs

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.LocationServices
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.databinding.FragmentPopularMoviesBinding
import com.udemy.startingpointpersonal.databinding.MovieItemBinding
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.ui.*
import com.udemy.startingpointpersonal.ui.view.popularMovs.adapter.*
import com.udemy.startingpointpersonal.ui.viewmodel.PopularMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@AndroidEntryPoint
class PopularMoviesFragment: BaseFragment<FragmentPopularMoviesBinding>() {

    companion object {
        const val DEFAULT_REGION = "US"
    }

    private val viewModel: PopularMoviesViewModel by viewModels()
    private var movies: List<Movie> = emptyList()
    private val layoutManager by lazy {
        GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
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

    private fun bindMovie(binding: MovieItemBinding, movie: Movie) = with(binding){
        url = movie.posterPath
        title = movie.title
    }
    /** Adapters */



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.hide()
        setBindings()
        setObservers()
    }

    private fun setBindings() = with(binding){
        lifecycleOwner = this@PopularMoviesFragment
        viewModel = this@PopularMoviesFragment.viewModel

        rvMovies.adapter = adapter
        rvMovies.layoutManager = layoutManager
    }

    private fun setObservers(){
        setOnScrollListener()
        //El launch lo uso solo para obtener la región
        viewLifecycleOwner.lifecycleScope.launch{
            val isLocationGranted = requestPermission()
            val region = getRegion(isLocationGranted)
            viewLifecycleOwner.launchAndCollect(viewModel.paginationLoading) {
                if (it){
                    activity?.muestraProgressBar()
                } else{
                    activity?.escondeProgressBar()
                }
            }
            //viewModel.moviesLD.observe(viewLifecycleOwner, ::handleResult)
            viewModel.getMoviesF(region).collect(::handleResult)
        }
    }

    private suspend fun requestPermission() : Boolean = suspendCancellableCoroutine{ continuation ->
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()){ isGranted ->
            continuation.resume(isGranted)
        }.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
    }


    //Tendremos un control asíncrono de lo que queremos que ocurra
    //"continuation" convertirá la parte asíncrona en una parte síncrona. video #44 de DevExperto
    //https://www.youtube.com/watch?v=DX-CIdg3jWY&list=PLrn69hTK5FByEfJEtLzJMEi0cKIwCVgJi
    //https://youtu.be/UOAV_yrTalo?list=PLrn69hTK5FByEfJEtLzJMEi0cKIwCVgJi&t=244
    @SuppressLint("MissingPermission")
    private suspend fun getRegion(isLocationGranted: Boolean): String = suspendCancellableCoroutine { continuation ->
            if (isLocationGranted) {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
                fusedLocationClient.lastLocation.addOnCompleteListener { taskLocation ->
                    continuation.resume(getRegionFromLocation(taskLocation.result))
                }
            } else {
                continuation.resume(DEFAULT_REGION)
            }
        }

    private fun getRegionFromLocation(location: Location) = Geocoder(requireContext())
        .getFromLocation(location.latitude, location.longitude, 1)?.firstOrNull()?.countryCode ?: DEFAULT_REGION

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

    private fun setOnScrollListener() = viewLifecycleOwner.launchAndCollect(binding.rvMovies.lastVisibleEvents) { itemPosition ->
        if(viewModel.lastVisible.value < itemPosition)
            viewModel.lastVisible.value = itemPosition
    }

    private fun onAction(action: Action) {
        when (action) {
            is Action.Click -> navigateToDetail(action.item as Movie)
            is Action.Delete -> TODO()
            is Action.Favorite -> TODO()
            is Action.Share -> TODO()
        }
    }

    private fun navigateToDetail(movie: Movie) = findNavController().navigate(
        PopularMoviesFragmentDirections.actionPopularMoviesFragmentToDetailFragment(movie)
    )

}

sealed interface ViewState<out T> {
    data object Loading: ViewState<Nothing>
    data class Success<T>(val data: T): ViewState<T>
    data class Error(val message: String): ViewState<Nothing>
}
