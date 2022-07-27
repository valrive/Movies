package com.udemy.startingpointpersonal.ui.popularMovs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udemy.startingpointpersonal.databinding.MovieItemBinding
import com.udemy.startingpointpersonal.pojos.Movie
import com.udemy.startingpointpersonal.utils.basicDiffUtil
import kotlin.properties.Delegates

class MovieAdapter(
    private val onAction: (Action) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        areItemsTheSame = {old, new -> old.id == new.id}
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding, onAction)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(movies[position])
        }
    }

    private inner class ItemViewHolder(
        val binding: MovieItemBinding,
        val onAction: (Action) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                root.setOnClickListener { onAction(Action.Click(movie)) }
                url = "https://image.tmdb.org/t/p/w500/${movie.poster_path}"
                title = movie.title

                //hacemos que funcione ellipsize
                tvTitle.isSelected = true
            }
        }
    }

}

sealed interface Action {
    class Click(val movie: Movie) : Action
    class Share(val movie: Movie) : Action
    class Favorite(val movie: Movie) : Action
    class Delete(val movie: Movie) : Action
}

