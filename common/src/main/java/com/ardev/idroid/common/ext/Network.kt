package com.ardev.idroid.common.ext

import android.content.Context
import android.net.*
import java.io.*

fun Context.isOnline(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager.activeNetworkInfo.also {
        return it != null && it.isConnected
    }
}

infix fun String.downloadTo(file: File) {

    URL(this).openStream().use { input ->
        file.outputStream().use { output -> input.copyTo(output) }
    }
}
