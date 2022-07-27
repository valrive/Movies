package com.udemy.startingpointpersonal.ui.popularMovs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udemy.startingpointpersonal.databinding.MovieItemBinding
import com.udemy.startingpointpersonal.pojos.Movie

//todo(Se puede cambiar el adapter a un tipo ListAdapter porque según es más rápido, además nos quitamos de andar pasando el listado)
class SingleMovieAdapter(
    private val list: List<Movie>,
    private val onAction: (Action) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding, onAction)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(list[position])
        }
    }

    private inner class ItemViewHolder(
        val binding: MovieItemBinding,
        val onAction: (Action) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding){
                root.setOnClickListener { onAction(Action.Click(movie)) }
                url = "https://image.tmdb.org/t/p/w500/${movie.poster_path}"
                title = movie.title

                //hacemos que funcione ellipsize
                tvTitle.isSelected = true
            }
        }
    }

}

@BindingAdapter("url")
fun ImageView.loadUrl(url: String){
    url.let {
        Glide.with(this)
            .load(it)
            .centerCrop()
            .into(this)
    }
}

sealed interface Action{
    class Click(val movie: Movie): Action
    class Share(val movie: Movie): Action
    class Favorite(val movie: Movie): Action
    class Delete(val movie: Movie): Action
}

