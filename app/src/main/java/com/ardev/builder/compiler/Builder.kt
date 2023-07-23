package com.ardev.builder.compiler

import androidx.annotation.MainThread
import com.ardev.builder.exception.CompilationFailure
import com.ardev.builder.project.api.Module
import java.io.IOException
import java.util.List

interface Builder<T : Module> {

    interface TaskListener {
        @MainThread
        fun onTaskStarted(name: String, message: String, progress: Int)
    }

    fun setTaskListener(taskListener: TaskListener)

    val module: T

    @Throws(CompilationFailure::class, IOException::class)
    fun build(type: BuildType)

    fun getTasks(type: BuildType): List<Task<in T>>
}
