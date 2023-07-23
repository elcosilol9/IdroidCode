package com.ardev.idroid.common.base

import android.content.Context
import com.ardev.api.actions.*
import com.ardev.idroid.app.IdroidApplication.context

abstract class BaseAction: Action {

 override fun update(data: ActionData) {
    
    presentation.apply {
     title = getTitle(context)
     icon = icon
    }
  }

 
  abstract fun getTitle(context: Context): String

  abstract val icon: Int 

  }