package com.ardev.idroid.common.ext

import android.widget.ImageView
import android.graphics.drawable.Drawable
import android.content.pm.PackageManager
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.Glide
import java.io.File
import com.ardev.idroid.app.IdroidApplication.context
import com.ardev.idroid.R.drawable

val File.iconFile: Any
	 get() {
 	 return if(isDirectory()) ic_folder 
 	 else if(extension == "xml") ic_xml
 	 else if(extension == "java") ic_java
 	 else if(extension == "kt") ic_kotlin
 	 else if(extension == "apk") apkIcon
 	 else if(isImage) this
 	 else ic_file
 
 }
 
  val File.optionalIconFile get() {
 	 return if(isDirectory()) ic_folder
	 else if(extension == "apk") ic_apk
 	 else if(isImage) ic_img
 	 else ic_unknown_file
 
 }


val File.apkIcon: Drawable get() {
    val info = context.packageManager.getPackageArchiveInfo(absolutePath, PackageManager.GET_ACTIVITIES)
        return info.applicationInfo.apply {
        sourceDir = absolutePath
        publicSourceDir = absolutePath
	}.also{
		it.loadIcon(context.packageManager)
	 }
}

fun ImageView.loadIcon(file: File) {
	Glide.with(this.context)
		.applyDefaultRequestOptions(RequestOptions().override(100).encodeQuality(80))
		.load(file.iconFile)
		.error(file.optionalIconFile)
		.placeholder(file.optionalIconFile)
		.into(this)
		if(file.isHidden()) applyTransparency()
		}

