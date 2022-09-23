package com.udemy.startingpointpersonal.ui.view.popularMovs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.databinding.MovieItemBinding
import com.udemy.startingpointpersonal.domain.model.Movie

class MovieAdapter(
    private val onAction: (Action) -> Unit
) :
/**
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
            is ItemViewHolder -> {

                holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context,
                R.anim.recycler_view_item_three)

                holder.bind(movie)
                //holder.itemView.setOnClickListener { onAction(Action.Click(movie)) }
            }
        }
    }

    private inner class ItemViewHolder(
        val binding: MovieItemBinding,
        val onAction: (Action) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        //Se setea el listener desde init para que no se esté re-seteando cada que se recicla la vista dentro de onBindViewHolder (Es más óptimo)
        init {
            binding.root.setOnClickListener {
                onAction(Action.Click(getItem(bindingAdapterPosition)))
            }
        }

        fun bind(movie: Movie) {
            with(binding) {
                //root.setOnClickListener { onAction(Action.Click(movie)) }
                url = movie.posterPath
                title = movie.title

                //hacemos que funcione ellipsize
                tvTitle.isSelected = true
            }
        }
    }

}

private class DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem == newItem

}

sealed interface Action {
    class Click(val item: Any) : Action
    class Share(val item: Any) : Action
    class Favorite(val item: Any) : Action
    class Delete(val item: Any) : Action
}

