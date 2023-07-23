package com.ardev.idroid.common.ext

import java.io.*
import java.nio.charset.Charset

fun InputStream.readText(charset: Charset = Charsets.UTF_8): String {
    return this.bufferedReader(charset).use { it.readText() }
}

infix fun InputStream.saveTo(file: File) {

    this.use { input ->
        file.outputStream().use { output -> input.copyTo(output) }
    }
}
