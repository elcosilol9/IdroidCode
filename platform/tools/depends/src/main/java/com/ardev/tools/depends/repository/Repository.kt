package com.ardev.tools.depends.repository

import java.io.File
import java.io.InputStream

interface Repository {
    val name: String 

    var cacheDirectory: File

    fun getInputStream(path: String): InputStream?

    fun getFile(path: String): File?

    fun getCachedFile(path: String): File?

    val rootDirectory: File
        get() = if (name.isNotEmpty()) {
            File(cacheDirectory, name)
        } else cacheDirectory
}