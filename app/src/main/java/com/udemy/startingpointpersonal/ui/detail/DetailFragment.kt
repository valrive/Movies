package com.udemy.startingpointpersonal.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.italic
import androidx.navigation.fragment.navArgs
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.data.pojos.Movie
import com.udemy.startingpointpersonal.databinding.FragmentDetailBinding
import com.udemy.startingpointpersonal.ui.BaseFragment
import com.udemy.startingpointpersonal.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class DetailFragment: BaseFragment<FragmentDetailBinding>(){

    private val args by navArgs<DetailFragmentArgs>()
    private val movie: Movie by lazy { args.movie }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (requireActivity() as MainActivity).supportActionBar
        actionBar?.show()
        actionBar?.title = movie.title

        binding.movie = movie

        //enchulamos un poco el font
        binding.txtLanguage.text = buildSpannedString {
            italic { append("${getString(R.string.label_languaje)} ")}
            appendLine(movie.originalLanguage)
        }

        //todo(FindMovieByIdUseCase)
    }
}