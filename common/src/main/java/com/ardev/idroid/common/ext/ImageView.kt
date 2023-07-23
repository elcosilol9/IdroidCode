package com.ardev.idroid.common.ext

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun ImageView.applyTransparency(transparency: Float = 0.5f) {
ColorMatrix().apply {
    setSaturation(1f) 
    setAlpha(transparency)
    }.also {
   colorFilter = ColorMatrixColorFilter(it)
   	 }
}
fun ImageView.loadImage(any: Any, optional: Int? = null, radius: Int = 0) = Glide.with(context)
        .load(any)
        .error(opcional)
        .placeholder(optional)
        .transform(RoundedCorners(radius))
        .into(this)
