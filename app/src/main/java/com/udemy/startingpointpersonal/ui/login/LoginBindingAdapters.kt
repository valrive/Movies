package com.udemy.startingpointpersonal.ui.login

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun View.bindVisible(visible: Boolean?){
    //para validar nullable se compara contra true, si visible es null entonces retorna false
    visibility = if(visible == true) View.VISIBLE else View.GONE
}