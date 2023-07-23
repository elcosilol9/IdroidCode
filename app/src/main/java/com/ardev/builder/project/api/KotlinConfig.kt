package com.ardev.builder.project.api

import java.io.File
import java.util.Map

interface KotlinModule : Module {
    
    val kotlinDir: File
    
    fun addKotlinFile(file: File)
    
    fun getKotlinFile(packageName: String): File?
    
    fin getKotlinFiles(): Map<String, File>
    
}
