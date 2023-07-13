package com.udemy.startingpointpersonal.ui.view.popularMovs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.databinding.MovieItemBinding
import com.udemy.startingpointpersonal.domain.model.Movie
import com.udemy.startingpointpersonal.ui.basicDiffUtil

/**
 * Adapter básico optimizado con basicDiffUtil, ya no lleva el list como parámetro
 */
class MovieAdapter2( private val onAction: (Action) -> Unit ) :
    ListAdapter<Movie, MovieAdapter2.ViewHolder>(TypedDiffUtilCallback()) {

    var movies: List<Movie> by basicDiffUtil(
        areItemsTheSame = {old, new -> old.id === new.id}
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onAction
        )

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.recycler_view_item_three
        )
        holder.bind(movies[position])
    }

    inner class ViewHolder(
        val binding: MovieItemBinding,
        val onAction: (Action) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        //Se inicializa el listener desde init para que no se esté re-seteando cada que se recicla la vista dentro de onBindViewHolder (Es más óptimo)
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



