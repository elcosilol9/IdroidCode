package com.ardev.api.actions

import android.view.Menu

abstract class ActionManager {

    companion object {
        val instance: ActionManager by lazy { ActionManagerImpl() }
    }

	abstract val actions: Map<String, Action>
	
    abstract fun fillMenu(menu: Menu, data: ActionData, location: String)

    abstract fun registerAction(action: Action)

    abstract fun unregisterAction(action: Action)

    abstract fun clear()
}
