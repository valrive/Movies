package com.udemy.startingpointpersonal.ui.home.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("url")
fun ImageView.loadUrl(url: String){
    Glide.with(this).load(url).centerCrop().into(this)
}