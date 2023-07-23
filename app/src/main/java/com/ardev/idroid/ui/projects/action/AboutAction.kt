package com.ardev.idroid.ui.projects.action

import android.content.Context
import com.ardev.idroid.ui.about.AboutActivity
import com.ardev.api.actions.*
import com.ardev.idroid.R

class AboutAction: ProjectsAction {

  override fun performAction(data: ActionData) {
    data.activity!!.launchActivity<AboutActivity>()
    }
  override fun getTitle(context: Context): String = context.getString(R.string.about)
  
  override val icon: Int = -1
  
  override val actionId: String = "projects.about"
  }