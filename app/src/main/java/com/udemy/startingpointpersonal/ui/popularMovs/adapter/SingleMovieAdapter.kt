package com.udemy.startingpointpersonal.ui.popularMovs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udemy.startingpointpersonal.databinding.MovieItemBinding
import com.udemy.startingpointpersonal.pojos.Movie

class SingleMovieAdapter(
    private val list: List<Movie>,
    private val itemClickListener: OnSingleMovieClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnSingleMovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = Item2ViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onMovieClick(list[position])
        }

        return holder
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is Item2ViewHolder -> holder.bind(list[position])
        }
    }

    private inner class Item2ViewHolder(
        val binding: MovieItemBinding
    //) : RecyclerView.ViewHolder(binding.root) {
    ) : BaseViewHolder<Movie>(binding.root) {

        override fun bind(item: Movie) {
            binding.url = "https://image.tmdb.org/t/p/w500/${item.poster_path}"
        }
    }

}

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}