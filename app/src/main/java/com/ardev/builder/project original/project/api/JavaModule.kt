package com.ardev.builder.project.api

import com.ardev.builder.model.Library
import java.io.File
import java.util.List
import java.util.Map
import java.util.Set

interface JavaModule : Module {

    val javaDir: File   

    fun addJavaFile(javaFile: File)
    
    fun getJavaFile(packageName: String): File

    fun removeJavaFile(packageName: String)
    
    fun getJavaFiles(): Map<String, File>

    fun addLibrary(jar: File)
    
    fun putLibraryHashes(hashes: Map<String, Library>)
    
    fun getLibrary(hash: String): Library?
    
    fun getLibraries(): List<File>
    
    fun addInjectedClass(file: File)
    
    fun getInjectedClasses(): Map<String, File>
     
    fun getAllClasses(): Set<String>

}