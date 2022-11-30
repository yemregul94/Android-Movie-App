package com.example.movieapp.util

import android.content.Context
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.R

fun ImageView.downloadFromUrl(url: String?, context: Context) {
    val options = RequestOptions()
        .error(R.drawable.bg_rectangle_darker_gray)
        .placeholder(CircularProgressDrawable(context).apply {
            strokeWidth = 8f
            centerRadius = 40f
            start()
        })

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .fitCenter()
        .into(this)
}

fun ImageButton.loadImage(res: Int, context: Context){
    Glide.with(context).load(res).into(this)
}


@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView, url: String?) {
    view.downloadFromUrl(url, view.context)
}

@BindingAdapter("isFavorites")
fun loadImage(view: ImageButton, isFavorites: Boolean){
    if(isFavorites){
        view.loadImage(R.drawable.ic_favorite, view.context)
    }else{
        view.loadImage(R.drawable.ic_not_favorite, view.context)
    }
}
