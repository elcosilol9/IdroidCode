package com.ardev.idroid.common

import com.ardev.idroid.app.IdroidApplication

object Paths {
    val dataDir = IdroidApplication.instance.getExternalFilesDir("").absolutePath
    val mediaDir = IdroidApplication.instance.getExternalMediaDirs()[0].absolutePath
    val cacheDir = IdroidApplication.instance.getExternalCacheDir("").absolutePath
	val appDir = "$mediaDir/IdroidCode"
    val projectsDir = "$appDir/projects"
    val templatesDir = "$appDir/templates"
    val pluginsDir = "$appDir/plugins"
  
}
