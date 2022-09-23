package com.udemy.startingpointpersonal.ui.view.popularMovs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class GRVAdapterL<T : Any>(
    private val list: List<T>,
    @LayoutRes private val layoutId: Int,
    private val setDataToView: (item: T, binding: ViewDataBinding) -> Unit
    //private val bindingInterface: GenericRecyclerAdapterBindingInterface<T>
) : RecyclerView.Adapter<GRVAdapterL<T>.ViewHolder>() {

    private lateinit var binding: ViewDataBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        binding = DataBindingUtil.bind(view)!!
        return ViewHolder(binding, setDataToView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.bind(list[position], bindingInterface)
        holder.bindData(list[position])
    }

    override fun getItemCount() = list.size


    inner class ViewHolder(
        private val binding: ViewDataBinding,
        private val setDataToView: (item: T, binding: ViewDataBinding) -> Unit
        ) : RecyclerView.ViewHolder(binding.root) {

        fun <T : Any> bind(
            item: T,
            bindingInterface: GenericRecyclerAdapterBindingInterface<T>
        ) = bindingInterface.bindData(item, binding)

        fun bindData(item: T) =
            setDataToView.invoke(item, binding)

    }

    interface GenericRecyclerAdapterBindingInterface<T> {
        fun bindData(item: T, binding: ViewDataBinding)
    }

}