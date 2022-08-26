package com.udemy.startingpointpersonal.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import java.lang.reflect.ParameterizedType

//open class BaseFragment<T: ViewBinding>(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {
open class BaseFragment<T: ViewDataBinding> : Fragment() {

    //declaración del binding
    private var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createBindingInstance(inflater, container).also { _binding = it }.root

    /** Creates new [T] instance using reflection. */
    @Suppress("UNCHECKED_CAST")
    protected open fun createBindingInstance(inflater: LayoutInflater, container: ViewGroup?): T {
        val vbType = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        val vbClass = vbType as Class<T>
        val method = vbClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)

        // Call VB.inflate(inflater, container, false) Java static method
        return method.invoke(null, inflater, container, false) as T
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}