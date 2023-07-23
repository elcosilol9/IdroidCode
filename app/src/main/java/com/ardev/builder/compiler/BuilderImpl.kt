package com.ardev.builder.compiler

import android.os.Handler
import android.os.Looper
import com.ardev.builder.exception.CompilationFailure
import com.ardev.builder.project.api.Module
import java.io.IOException

abstract class BuilderImpl<T : Module>(private val project: T) : Builder<T> {

    private val mainHandler: Handler = Handler(Looper.getMainLooper())
    private val _tasksRan: MutableList<Task<in T>> = mutableListOf()
    val tasksRan: List<Task<in T>> = _tasksRan
    private var taskListener: TaskListener? = null

    override fun setTaskListener(taskListener: TaskListener) {
        this.taskListener = taskListener
    }

    override val module: T = project
    

    protected fun updateProgress(name: String, message: String, progress: Int) {
        taskListener?.onTaskStarted(name, message, progress)
    }

    @Throws(CompilationFailure::class, IOException::class)
    override fun build(type: BuildType) {
        _tasksRan.clear()
        val tasks: List<Task<in T>> = getTasks(type)
        for ((index, task) in tasks.withIndex()) {
            val current = index.toFloat()
            mainHandler.post {
                updateProgress(
                    task.name,
                    "Task started",
                    ((current / tasks.size.toFloat()) * 100f).toInt()
                )
            }
            try {
                task.prepare(type)
                task.run()
            } catch (e: Throwable) {
                if (e is OutOfMemoryError) {
                    tasks.clear()
                    _tasksRan.clear()
                    throw CompilationFailure("Builder ran out of memory", e)
                }
                task.clean()
                _tasksRan.forEach { it.clean() }
                throw e
            }
            _tasksRan.add(task)
        }
        _tasksRan.forEach { it.clean() }
    }

    abstract override fun getTasks(type: BuildType): List<Task<in T>>
   
}
