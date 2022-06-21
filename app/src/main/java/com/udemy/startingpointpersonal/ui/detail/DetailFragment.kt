package com.udemy.startingpointpersonal.ui.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.databinding.FragmentDetailBinding
import com.udemy.startingpointpersonal.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment: BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail){

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //_binding = FragmentDetailBinding.bind(view)

        with(binding){
            movie = args.movie

            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500/${args.movie.poster_path}")
                .centerCrop().into(imgMovie)

            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500/${args.movie.backdrop_path}")
                .centerCrop().into(imgBackground)
        }
    }
}