package com.ardev.idroid.ui.projects

import android.content.Context
import java.io.File
import java.lang.ref.WeakReference
import com.ardev.builder.project.Project
import com.ardev.idroid.common.ext.*

object ProjectProvider {
    private lateinit var ref: WeakReference<Project>

    fun init(project: Project) {
        ref = WeakReference(project)
    }

    fun getProject(): Project {
        if (ref.get() == null) restartApp() 
        return ref.get()!!
    }
}
