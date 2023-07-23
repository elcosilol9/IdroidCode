package com.ardev.builder.project.impl

import com.ardev.builder.project.api.Module
import java.io.File
import java.io.IOException

class ModuleImpl(private val root: File) : Module {
    
    override val rootFile: File = root
    
    override val libsDir: File = File(rootFile, "libs")
    
    override val buildDir: File = File(rootFile, "build")
    
    override val cacheDir: File = File(buildDir, ".cache")
               
    override fun open() {    
       resolveDirs(buildDir, cacheDir)
        
        
    }

    override fun clear() {
     
    }

    override fun index() {
        
    }
                        
    override fun hashCode(): Int {
        return root.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ModuleImpl) return false
        return root == other.root
    }
}
