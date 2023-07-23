package com.ardev.idroid.common.ext

import java.util.zip.*
import java.io.*
import java.nio.file.*
import java.net.*

fun String.toFile() = File(this)

fun String.toZipFile() = ZipFile(this)

fun File.walkByExtension(extension: String) = walkTopDown().filter { it.isFile && it.extension == extension } 


val File.mimeType: String? get() = Files.probeContentType(Paths.get(this.path))

val File.isImage get() = mimeType?.startsWith("image") ?: false

val File.resolveDirs: Boolean
get() = if (!this.exists()) this.mkdirs() else true
 
 
 fun resolveDirs(vararg files: File) {   
    files.forEach {
      it.resolveDirs
    }
}
 
val File.isMissing get() = this == null || !exists()