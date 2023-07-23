package com.ardev.builder.project.api

import java.io.File

interface Module {

    val rootFile: File

    val name: String = rootFile.name   
                
    val pathConfig: PathConfig  
        
    fun open()    

    fun index()
    
    fun clear()
    
}
