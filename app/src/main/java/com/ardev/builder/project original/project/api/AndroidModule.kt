package com.ardev.builder.project.api

import java.io.File

interface AndroidModule : JavaModule, KotlinModule {
    val resDir: File
    
    val assetsDir: File
     
    val jniLibs: File

    val manifestFile: File

    val packageName: String

    val targetSdk: Int

    val minSdk: Int
    
    fun addRClass(file: File)
    
    fun getRClasses(): Map<String, File>

   
}
