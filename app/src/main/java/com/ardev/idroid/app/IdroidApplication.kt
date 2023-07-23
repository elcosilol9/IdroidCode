package com.ardev.idroid.app

import android.app.Activity
import android.app.Application
import android.content.Context
import com.ardev.tools.parser.proxy.ProxyResources
import kotlin.properties.Delegates
import com.ardev.idroid.common.ext.*


class IdroidApplication: Application() {


	override fun onCreate() {
			super.onCreate()
			instance = this
	DynamicColors.applyToActivitiesIfAvailable(this)
	
      inParallel {
			ProxyResources.init(applicationContext)
			
            
       
		}
	
	}

 
companion object {
        var instance by Delegates.notNull<IdroidApplication>()
        var context: Context = instance.applicationContext
        
		 @JvmStatic
		 val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        
        
	}

}

