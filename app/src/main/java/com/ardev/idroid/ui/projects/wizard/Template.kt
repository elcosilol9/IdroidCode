package com.ardev.idroid.ui.home.wizard

import java.io.File
import com.google.gson.Gson
import com.ardev.idroid.common.ext.*

data class Template(
    val name: String,
    val minSdk: Int = 21,
    val javaSupport: Boolean = false,
    val kotlinSupport: Boolean = false
) {
	lateinit var path: String

    companion object {
        fun fromFile(parent: File): Template? {
        val manifest = File(parent, "Manifest.json")
            if (!parent.exists() || !parent.isDirectory()) return null
            if (!manifest.exists()) return null
            
            runCatching {
                manifest.readText().toBean<Template>()
                    .apply { path = parent.absolutePath }
            }.onFailure {
                null
            }
        }
	}
}    	