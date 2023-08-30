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
    //: ListAdapter<Movie, MovieAdapterLA.ItemViewHolder>(TypedDiffUtilCallback()) {
    //: ListAdapter<Movie, MovieAdapterLA.ViewHolder>(DiffUtilCallback) {
    : ListAdapter<Movie, MovieAdapterLA.ViewHolder>(basicDiffUtil{ old, new ->
    old.id == new.id
}) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterLA.ViewHolder =
        ViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onAction
        )

    override fun onBindViewHolder(holder: MovieAdapterLA.ViewHolder, position: Int) {
        holder.apply {
            itemView.animation = AnimationUtils.loadAnimation(
                itemView.context,
                R.anim.recycler_view_item_three
            )
            bind(getItem(position))
        }
    }

    inner class ViewHolder(
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