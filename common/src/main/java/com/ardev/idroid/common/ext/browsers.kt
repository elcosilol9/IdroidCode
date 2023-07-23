package com.ardev.idroid.common.ext

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

fun Context.launchUrl(uri: Uri) = CustomTabsIntent.Builder()
.build()
.launchUrl(this, uri)
