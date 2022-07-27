package com.udemy.startingpointpersonal.ui.popularMovs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udemy.startingpointpersonal.databinding.MovieItemBinding
import com.udemy.startingpointpersonal.data.pojos.Movie

class MovieAdapter(
    private val onAction: (Action) -> Unit
) : /**
    Lo que está comentado se descomentará para utilizar la versión de siempre con RecyclerView.Adapter
    */
    //RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    ListAdapter<Movie, RecyclerView.ViewHolder>(DiffUtilCallback()) {

    /*var movies: List<Movie> by basicDiffUtil(
        areItemsTheSame = {old, new -> old.id == new.id}
    )*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding, onAction)
    }

    //override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position)//movies[position]
        when (holder) {
            is ItemViewHolder -> holder.bind(movie)
        }
    }

    private inner class ItemViewHolder(
        val binding: MovieItemBinding,
        val onAction: (Action) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                root.setOnClickListener { onAction(Action.Click(movie)) }
                url = movie.posterPath
                title = movie.title

                //hacemos que funcione ellipsize
                tvTitle.isSelected = true
            }
        }
    }

}

private class DiffUtilCallback : DiffUtil.ItemCallback<Movie>(){
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem == newItem

}

sealed interface Action {
    class Click(val movie: Movie) : Action
    class Share(val movie: Movie) : Action
    class Favorite(val movie: Movie) : Action
    class Delete(val movie: Movie) : Action
}

