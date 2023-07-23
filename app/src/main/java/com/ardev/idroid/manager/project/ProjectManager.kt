package com.ardev.idroid.manager.project

import java.io.File
import java.util.ArrayList
import com.ardev.builder.compiler.BuildType
import com.ardev.builder.exception.CompilationFailedException
import com.ardev.builder.project.Project
import com.ardev.builder.project.api.AndroidModule
import com.ardev.builder.project.api.JavaModule
import com.ardev.builder.project.api.Module
import com.ardev.idroid.common.ext.*
import kotlinx.coroutines.*

object ProjectManager {

    @Volatile
    private var mCurrentProject: Project? = null
    private val mProjectOpenListeners = ArrayList<OnProjectOpenListener>()

    fun addOnProjectOpenListener(listener: OnProjectOpenListener) {
        mCurrentProject?.let { listener.onProjectOpen(it) }
        mProjectOpenListeners.add(listener)
    }

    fun removeOnProjectOpenListener(listener: OnProjectOpenListener) {
        mProjectOpenListeners.remove(listener)
    }

    fun openProject(project: Project, downloadLibs: Boolean, listener: TaskListener) {
        mCurrentProject = project
        var shouldReturn = false
        doInBg {
            runCatching {
                mCurrentProject?.open()
            }.onFailure {
                shouldReturn = true
            }

            mProjectOpenListeners.forEach { it.onProjectOpen(mCurrentProject) }

            if (shouldReturn) {
                withContext(Dispatchers.Main) {
                    listener.onComplete(project, false, "Failed to open project.")
                }
                return@doInBg
            }

            runCatching {
                mCurrentProject?.index()
            }.onFailure {
                shouldReturn = true
            }

            if (shouldReturn) {
                withContext(Dispatchers.Main) {
                    listener.onComplete(project, false, "Failed to indexing project.")
                }
                return@doInBg
            }

            val module = mCurrentProject?.getMainModule()

            if (module is JavaModule) {
                val javaModule = module as JavaModule

                runCatching {
                withContext(Dispatchers.Main) {
                    listener.onTaskStarted("Downloading files...") 
                    }
                }.onFailure {

                }

            }

            if (module is AndroidModule) {
             withContext(Dispatchers.Main) {
                listener.onTaskStarted("Generating resource files.")
                }
                runCatching {

                }.onFailure {

                }
            }
            if (module is JavaModule) {
                if (module is AndroidModule) {
                withContext(Dispatchers.Main) {
                    listener.onTaskStarted("Indexing XML files.")
                    }
                }
                
                withContext(Dispatchers.Main) {
                    listener.onTaskStarted("Indexing")
                    }
                runCatching {
                    if (module is AndroidModule) {

                    }
                }.onFailure {

                }

            }
            withContext(Dispatchers.Main) {
                delay(1000)
                listener.onComplete(project, true, "Index successful")
            }
        }
    }

    fun closeProject(project: Project) {
        if (project == mCurrentProject) {
            mCurrentProject = null
        }
    }

    val currentProject: Project?
        get() = mCurrentProject

interface OnProjectOpenListener {
        fun onProjectOpen(project: Project)
    }
    
interface TaskListener {
        fun onStarted(message: String)
        fun onCompleted(project: Project, success: Boolean, message: String)
    }
        
}