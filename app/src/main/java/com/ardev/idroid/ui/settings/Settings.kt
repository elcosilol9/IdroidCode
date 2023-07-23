package com.ardev.idroid.ui.settings

import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import com.google.android.material.color.DynamicColors
import com.ardev.idroid.app.IdroidApplication

const val THEME = "theme"
const val DYNAMIC_THEME = "dynamic_theme"  
const val FONT_SIZE = "font_size"
const val JAVA_VERSION = "java_version"

    
    
    val theme: Int
        get() =
            prefs.getInt(
                THEME,
                MODE_NIGHT_FOLLOW_SYSTEM
            )

    val isDynamicTheme: Boolean
        get() =
            DynamicColors.isDynamicColorAvailable() &&
                prefs.getBoolean(
                    DYNAMIC_THEME,
                    false
                )


    val fontSize: Int
        get() =
            prefs.getInt(
                FONT_SIZE,
                12
            )

    val javaVersion: Int
        get() =
            prefs.getInt(
                JAVA_VERSION,
                7
            )



 	fun subscribe(listener: OnSharedPreferenceChangeListener) {
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unsubscribe(listener: OnSharedPreferenceChangeListener) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener)
    }
    
 	val prefs = IdroidApplication.preferences
 
