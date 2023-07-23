package com.ardev.idroid.common.ext

import android.app.Activity
import android.content.*
import androidx.annotation.StyleRes
import androidx.core.app.ActivityCompat

fun Activity.recreateCompat() {
    ActivityCompat.recreate(this)
}

fun Activity.setThemeCompat(@StyleRes resid: Int) {
    setTheme(resid)
}

inline fun <reified T : Activity> Context.launchActivity() {
    startActivity(Intent(this, T::class.java))
}
