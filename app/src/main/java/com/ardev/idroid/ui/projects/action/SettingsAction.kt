package com.ardev.idroid.ui.projects.action

import android.content.Context
import com.ardev.api.actions.*
import com.ardev.idroid.common.ext.*
import com.ardev.idroid.ui.settings.SettingActivity
import com.ardev.idroid.R

class SettingsAction: ProjectsAction {

  override fun performAction(data: ActionData) {
   data.activity!!.launchActivity<SettingActivity>()
    }

  override fun getTitle(context: Context): String = context.getString(R.string.settings)
  
  override val icon: Int = -1
  
  override val actionId: String = "projects.settings"
  }