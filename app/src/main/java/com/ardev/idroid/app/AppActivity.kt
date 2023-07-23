package com.ardev.idroid.app

import android.content.Context
import android.os.Bundle
import android.content.SharedPreferences
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ardev.idroid.app.IdroidApplication
import androidx.core.view.WindowCompat
import com.ardev.idroid.ui.settings.isDynamicTheme
import com.ardev.idroid.common.ext.addSystemWindowInsetToPadding

abstract class AppActivity: AppCompatActivity() {
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
		WindowCompat.setDecorFitsSystemWindows(window, false)
        rootView.addSystemWindowInsetToPadding(false, false, false, false)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
	
    }


    private val rootView get() = window.decorView.findViewById(android.R.id.content)
    
}