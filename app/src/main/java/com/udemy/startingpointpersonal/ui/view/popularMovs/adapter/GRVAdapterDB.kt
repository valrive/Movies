package com.udemy.startingpointpersonal.ui.view.popularMovs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.udemy.startingpointpersonal.R

class GRVAdapterDB<T : Any, VDB: ViewDataBinding>(
    val data: List<T>,
    val onBind: (VDB, T, Int) -> Unit,
    val inflate: (layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> VDB
) : RecyclerView.Adapter<GRVAdapterDB<T, VDB>.BaseHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder =
        BaseHolder(inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        //Animamos la presentación de los items
        holder.itemView.animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.recycler_view_item_three
        )
        onBind(holder.binding, data[position], data.size)
    }

    //override fun getItemViewType(position: Int) = layoutId

    override fun getItemCount() = data.size

    inner class BaseHolder(
        val binding: VDB
    ) : RecyclerView.ViewHolder(binding.root) {
    }

}