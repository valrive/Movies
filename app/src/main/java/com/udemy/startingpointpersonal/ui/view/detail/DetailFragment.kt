package com.udemy.startingpointpersonal.ui.view.detail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.navArgs
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.data.model.Movie
import com.udemy.startingpointpersonal.databinding.FragmentDetail2Binding
import com.udemy.startingpointpersonal.ui.BaseFragment
import com.udemy.startingpointpersonal.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment: BaseFragment<FragmentDetail2Binding>(){

    private val args by navArgs<DetailFragmentArgs>()
    private val movie: Movie by lazy { args.movie }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setSupportActionBar(view.findViewById(R.id.toolbar))
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = movie.title
        activity?.window?.statusBarColor = android.R.color.transparent

        binding.movie = movie

        //enchulamos un poco el font
        /*binding.txtLanguage.text = buildSpannedString {
            italic { append("${getString(R.string.label_languaje)} ")}
            appendLine(movie.originalLanguage)
        }*/

        //todo(FindMovieByIdUseCase)
    }

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        val inflater = super.onGetLayoutInflater(savedInstanceState)
        val contextThemeWrapper: Context = ContextThemeWrapper(requireContext(), R.style.AppTheme_Detail)
        return inflater.cloneInContext(contextThemeWrapper)
    }

}
/*
class DetailFragment: BaseFragment<FragmentDetail3Binding>(){

    private val args by navArgs<DetailFragmentArgs>()
    private val movie: Movie by lazy { args.movie }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = movie.title

        binding.movie = movie

        //enchulamos un poco el font
        */
/*binding.txtLanguage.text = buildSpannedString {
            italic { append("${getString(R.string.label_languaje)} ")}
            appendLine(movie.originalLanguage)
        }*//*


        //todo(FindMovieByIdUseCase)
    }
}*/
