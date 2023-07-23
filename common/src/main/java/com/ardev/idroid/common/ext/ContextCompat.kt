package com.ardev.idroid.common.ext

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.TintTypedArray
import androidx.core.content.ContextCompat
import com.ardev.idroid.app.IdroidApplication


@ColorInt
fun getColor(@ColorRes id: Int): Int = IdroidApplication.context.getColorStateListCompat(id).defaultColor

fun getDrawable(@DrawableRes id: Int): Drawable =
    AppCompatResources.getDrawable(IdroidApplication.context, id)!!

fun Context.getColorStateListCompat(@ColorRes id: Int): ColorStateList =
    AppCompatResources.getColorStateList(this, id)!!



