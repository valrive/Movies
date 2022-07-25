package com.udemy.startingpointpersonal.ui.popularMovs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udemy.startingpointpersonal.databinding.MovieItemBinding
import com.udemy.startingpointpersonal.pojos.Movie
import com.udemy.startingpointpersonal.ui.popularMovs.Action

class SingleMovieAdapter(
    private val list: List<Movie>,
    private val onAction: (Action) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding, onAction)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(list[position])
        }
    }

    private inner class ItemViewHolder(
        val binding: MovieItemBinding,
        val onAction: (Action) -> Unit
        //) : RecyclerView.ViewHolder(binding.root) {
    ) : BaseViewHolder<Movie>(binding.root) {

        override fun bind(movie: Movie) {
            binding.root.setOnClickListener { onAction(Action.Click(movie)) }

            binding.url = "https://image.tmdb.org/t/p/w500/${movie.poster_path}"
        }
    }

}

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}
