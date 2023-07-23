package com.ardev.idroid.common.ext

import java.io.File

// com.example.app
val File?.packageName: String?
	get() {
    if (this == null || !isDirectory) return null

    var directory = this
    var original = this
    while (!directory.isJavaOrKotlinFolder) {
        if (directory == null) return null
        directory = directory.parentFile
    }

    val originalPath = original.absolutePath
    val javaOrKotlinPath = directory.absolutePath
    val cutPath = originalPath.replace(javaOrKotlinPath, "")
     if (cutPath.startsWith("/")) {
        cutPath = cutPath.substring(1)
    }
    if (cutPath.endsWith("/")) {
        cutPath = cutPath.substring(0, cutPath.length - 1)
    }
    
    return cutPath.replace("/", ".")
}

val File.isJavaFile get() = isFile() && extension == "java"
val File.isKotlinFile get() = isFile() && extension == "kt"
val File.isXMLFile get() = isFile() && extension == "xml"

fun File.isResourceXMLFileInDir(directoryName: String) = parentFile.name.startsWith(directoryName) && isResourceXMLFile

// res/drawable, res/layout, etc
val File.isResourceXMLDir
	get() {
    val res = parentFile
    val main = res.parentFile
	val src = main.parentFile
	if (res == null && main == null && src == null) return false
    else return res.name == "res" && main.name == "main" && src.name == "src"
}

val File.isResourceXMLFile get() = isXMLFile && parentFile.isResourceXMLDir





private val File.isJavaOrKotlinFolder
	get() {
    if (!isDirectory) return false

    if (name == "java" || name == "kotlin" ) {
        val main = parentFile
        val src = main.parentFile
        if (main == null && src == null) return false
         else return main.name == "main" && src.name == "src" 
    }

    return false
}