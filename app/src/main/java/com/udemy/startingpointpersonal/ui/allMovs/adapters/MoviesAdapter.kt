package com.udemy.startingpointpersonal.ui.allMovs.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udemy.startingpointpersonal.core.BaseViewHolder
import com.udemy.startingpointpersonal.databinding.MovieItemBinding
import com.udemy.startingpointpersonal.pojos.Movie

class MoviesAdapter(
        private val moviesList: List<Movie>,
        private val itemClickListener: OnMovieClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MoviesViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onMovieClick(moviesList[position])
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MoviesViewHolder -> holder.bind(moviesList[position])
        }
    }

    override fun getItemCount(): Int = moviesList.size

    private inner class MoviesViewHolder(
            val binding: MovieItemBinding,
            val context: Context
    ) : BaseViewHolder<Movie>(binding.root) {
        override fun bind(item: Movie) {
            binding.url = "https://image.tmdb.org/t/p/w500/${item.poster_path}"
            //Glide.with(context).load("https://image.tmdb.org/t/p/w500/${item.poster_path}").centerCrop().into(binding.imgMovie)
        }
    }
}