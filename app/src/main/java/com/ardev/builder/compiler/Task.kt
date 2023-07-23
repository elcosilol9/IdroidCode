package com.ardev.builder.compiler

import com.ardev.builder.exception.CompilationFailure
import com.ardev.builder.project.api.Module
import java.io.IOException


abstract class Task<T : Module>(private val project: T) {

    val module: T = project
    
    abstract val name: String

    @Throws(IOException::class)
    abstract fun prepare(type: BuildType)

    @Throws(IOException::class, CompilationFailure::class)
    abstract fun run()

    fun clean() {}
        
}
