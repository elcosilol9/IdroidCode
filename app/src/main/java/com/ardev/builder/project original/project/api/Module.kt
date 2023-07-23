package com.ardev.builder.project.api

import java.io.File

interface Module {

    val rootFile: File

    val name: String = rootFile.name
    
    val libsDir: File
    
    val buildDir: File

    val cacheDir: File
        
    fun open()
    
    fun clear()

    fun index()
    
}
