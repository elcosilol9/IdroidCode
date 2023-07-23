package com.ardev.idroid.common.ext

import android.content.*
import java.io.*
import java.util.zip.*

fun Context.unzipFromAssets(zipFile: String, destination: String = this.filesDir.absolutePath) {
    try {
        assets.open(zipFile).use { stream -> 
            stream.unzip(destination)
        }
    } catch (e: IOException) {
       
    }
}

fun String.unzip(location: String) {
    try {
        FileInputStream(this).use { input ->
            input.unzip(location)
        }
    } catch (e: IOException) {
       
    }
}

private fun InputStream.unzip(destination: String) {
    checkDir(destination, "")
    val buffer = ByteArray(1024 * 10)
    ZipInputStream(BufferedInputStream(stream)).use { zin ->
        var ze: ZipEntry?
        while (zin.nextEntry.also { ze = it } != null) {
            if (ze!!.isDirectory) {
                checkDir(destination, ze!!.name)
            } else {
                val file = File(destination, ze!!.name)
                if (!file.exists()) {
                    if (!file.parentFile?.exists() && !file.parentFile!!.mkdirs()) {
                        continue
                    }
                    if (!file.createNewFile()) {
                        continue
                    }
                    FileOutputStream(file).use { fout ->
                        var count: Int
                        while (zin.read(buffer).also { count = it } != -1) {
                            fout.write(buffer, 0, count)
                        }
                    }
                    zin.closeEntry()
                }
            }
        }
    }
}

private fun checkDir(destination: String, dir: String) {
    val file = File(destination, dir)
    if (!file.isDirectory) file.mkdirs()
}