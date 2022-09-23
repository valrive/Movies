package com.udemy.startingpointpersonal.ui.view.popularMovs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udemy.startingpointpersonal.R

class GLAdapterL<T : Any>(
    @LayoutRes val layoutId: Int,
    inline val bind: (item: T, holder: GLAdapterL<T>.BaseViewHolder, itemCount: Int, binding: ViewDataBinding) -> Unit,
    private val onAction: (Action) -> Unit
) : ListAdapter<T, GLAdapterL<T>.BaseViewHolder>(BaseItemCallback<T>()) {

    private lateinit var binding: ViewDataBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            layoutId,
            parent,
            false
        )
        binding = DataBindingUtil.bind(view)!!
        return BaseViewHolder(binding, onAction)

        //val root = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        //return BaseViewHolder(root as ViewGroup)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        //Animamos la presentación de los items
        holder.itemView.animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.recycler_view_item_three
        )
        bind(getItem(position), holder, itemCount, binding)
    }

    override fun getItemViewType(position: Int) = layoutId

    //override fun getItemCount() = currentList.size

    inner class BaseViewHolder(
        val binding: ViewDataBinding,
        val onAction: (Action) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onAction(Action.Click(getItem(bindingAdapterPosition)))
            }
        }
    }

}


