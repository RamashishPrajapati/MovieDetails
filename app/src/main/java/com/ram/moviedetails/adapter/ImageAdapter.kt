package com.ram.moviedetails.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ram.moviedetails.R


@BindingAdapter("imageFromUrl")
fun ImageView.imageFromUrl(url: String?) {
    Glide.with(this.context).load(url).placeholder(R.drawable.halloween).error(R.drawable.mask)
        .into(this)

}
