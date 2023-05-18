package com.udemy.startingpointpersonal.ui.view.popularMovs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.databinding.MovieItemBinding
import com.udemy.startingpointpersonal.domain.model.Movie

/**
 * Adapter que incluye ListAdapter por lo que no es necesario pasar la lista como parámetro
 */
class MovieAdapterLA( private val onAction: (Action) -> Unit)
    : ListAdapter<Movie, RecyclerView.ViewHolder>(DiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ItemViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onAction
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.recycler_view_item_three
        )
        ( holder as? ItemViewHolder)?.bind(getItem(position))
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
                url = movie.posterPath
                title = movie.title
                //hacemos que funcione ellipsize
                tvTitle.isSelected = true
            }
        }
    }

}