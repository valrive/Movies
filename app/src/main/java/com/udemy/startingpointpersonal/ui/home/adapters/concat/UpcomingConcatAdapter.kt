package com.udemy.startingpointpersonal.ui.home.adapters.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udemy.startingpointpersonal.core.BaseConcatHolder
import com.udemy.startingpointpersonal.databinding.UpcomingMovieRowBinding
import com.udemy.startingpointpersonal.ui.home.adapters.MoviesAdapter

class UpcomingConcatAdapter(private val moviesAdapter: MoviesAdapter): RecyclerView.Adapter<BaseConcatHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding = UpcomingMovieRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConcatViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when (holder) {
            is ConcatViewHolder -> holder.bind(moviesAdapter)
            else -> throw IllegalArgumentException("No viewholder to show this data, did you forgot to add it to the onBindViewHolder?")
        }
    }

    override fun getItemCount(): Int = 1

    private inner class ConcatViewHolder(val binding: UpcomingMovieRowBinding): BaseConcatHolder<MoviesAdapter>(binding.root){
        override fun bind(adapter: MoviesAdapter) {
            binding.rvUpcomingMovies.adapter = adapter
        }
    }
}