package com.udemy.startingpointpersonal.ui.view.popularMovs.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udemy.startingpointpersonal.R

class GLAdapterDB<T : Any, VDB: ViewDataBinding>(
    private val inflate: (layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> VDB,
    private val onBind: (VDB, T, Int) -> Unit,
    private val onAction: (Action) -> Unit
) : ListAdapter<T, GLAdapterDB<T, VDB>.BaseHolder>(BaseItemCallback<T>()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder =
        BaseHolder(
            inflate(LayoutInflater.from(parent.context), parent, false),
            onAction
        )


    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        //Animamos la presentación de los items
        holder.itemView.animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.recycler_view_item_three
        )
        onBind(holder.binding, getItem(position), itemCount)
    }

    inner class BaseHolder(
        val binding: VDB,
        private val onAction: (Action) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onAction(Action.Click(getItem(bindingAdapterPosition)))
            }
        }
    }

}


class BaseItemCallback<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.toString() == newItem.toString()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}