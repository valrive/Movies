package com.udemy.startingpointpersonal.ui.view.popularMovs.adapter

import androidx.recyclerview.widget.DiffUtil
import com.udemy.startingpointpersonal.domain.model.Movie

class DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem == newItem

}