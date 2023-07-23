package com.ardev.idroid.common.ext

import android.content.*
import android.content.res.Configuration
import androidx.core.content.FileProvider
import java.io.File
import com.ardev.idroid.app.IdroidApplication
import kotlin.system.exitProcess


inline val versionCode: Int
    get() {
    val context = IdroidApplication.context
     return context.packageManager.getPackageInfo(context.packageName, 0).versionCode
}

inline val versionName: String
    get() {
    val context = IdroidApplication.context
    return context.packageManager.getPackageInfo(context.packageName, 0).versionName
}

fun restartApp() {
    exitProcess(0)
}

fun isDarkMode() = IdroidApplication.context.isDarkMode()

fun Context.isDarkMode(): Boolean {
            val darkModeFlag = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
        }

fun Context.installApk(path: String) {
Intent(Intent.ACTION_VIEW).apply {
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION and Intent.FLAG_ACTIVITY_NEW_TASK
        setDataAndType(FileProvider.getUriForFile(this, packageName, path.toFile()), "application/vnd.android.package-archive");
    }.also{
    startActivity(it)
    }
}



