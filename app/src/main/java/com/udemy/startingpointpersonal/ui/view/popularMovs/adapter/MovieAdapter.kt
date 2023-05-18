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
import com.udemy.startingpointpersonal.ui.basicDiffUtil

class MovieAdapter(
    private val onAction: (Action) -> Unit
) :
/**
Lo que está comentado se descomentará para utilizar la versión de siempre con RecyclerView.Adapter
 */
RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        areItemsTheSame = {old, new -> old.id === new.id}
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding, onAction)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = movies[position]
        when (holder) {
            is ItemViewHolder -> {

                holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context,
                R.anim.recycler_view_item_three)

                holder.bind(movie)
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
                onAction(Action.Click(movies[bindingAdapterPosition]))
            }
        }

        fun bind(movie: Movie) {
            with(binding) {
                url = movie.posterPath
                title = movie.title

                //hacemos que funcione ellipsize
                tvTitle.isSelected = true
            }
        }
    }

}



