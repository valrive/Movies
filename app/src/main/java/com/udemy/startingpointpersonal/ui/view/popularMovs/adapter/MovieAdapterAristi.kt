package com.udemy.startingpointpersonal.ui.view.popularMovs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.databinding.MovieItemBinding
import com.udemy.startingpointpersonal.domain.model.Movie

/**
 * Adapter básico optimizado con DiffUtil
 */
class MovieAdapterAristi(
    private var list: List<Movie>,
    private val onAction: (Action) -> Unit
) : RecyclerView.Adapter<MovieAdapterAristi.ItemViewHolder>() {

    
    fun submitList(newList: List<Movie>){
        val movieDiff = MovieDiffUtil(list, newList)
        val result = DiffUtil.calculateDiff(movieDiff)
        list = newList
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onAction
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.recycler_view_item_three
        )
        holder.bind(list[position])
    }

    inner class ItemViewHolder(
        val binding: MovieItemBinding,
        val onAction: (Action) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        //Se inicializa el listener desde init para que no se esté re-seteando cada que se recicla la vista dentro de onBindViewHolder (Es más óptimo)
        init {
            binding.tvTitle.setOnClickListener {
                onAction(Action.Click(list[bindingAdapterPosition]))
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



