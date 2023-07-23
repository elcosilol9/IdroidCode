package com.ardev.idroid.ui.projects.action

import android.content.Context
import com.ardev.api.actions.*
import com.ardev.idroid.ui.projects.ProjectsActivity
import com.ardev.idroid.common.base.BaseAction

abstract class ProjectsAction: BaseAction {

 override fun update(data: ActionData) {
     if (data.activity == null) return
     super.update(data)
     
  }

     override val location: String = Locations.MAIN_TOOLBAR
   
     val ActionData.activity: ProjectsActivity = this.get(ProjectsActivity::class.java)
  
  }