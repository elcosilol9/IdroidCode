package com.ardev.builder.project

import java.io.File
import java.io.IOException
import com.ardev.builder.project.api.Module
import com.ardev.builder.project.impl.AndroidModuleImpl

class Project(private val root: File) {
      
    val rootFile: File = root
      
    val name: String = rootFile.name
      
    val mainModule: Module lazy by { createMainModule(rootFile) }
    
    @Volatile            
    var isCompiling: Boolean = false
       
    fun addModule(library: File) {
       
    }

    

  
    fun open() {
        mainModule.open()
    }

   
    fun index() {
        mainModule.clear()
        mainModule.index()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val project = o as Project
        return root == project.root
    }

    override fun hashCode(): Int {
        return root.hashCode()
    }
}
