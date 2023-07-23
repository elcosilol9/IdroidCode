package com.ardev.api.actions

import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import java.util.LinkedHashMap

class ActionManagerImpl : ActionManager() {

    private val _actions = LinkedHashMap<String, Action>()
	override val actions: Map<String, Action> = _actions
	
    override fun fillMenu(menu: Menu, data: ActionData, location: String) {
        _actions.values.filter { it.location == location }.forEach { action ->
            action.update(data)
            fillMenu(menu, action, data)
        }
    }

    private fun fillMenu(menu: Menu, action: Action, data: ActionData) {
        val presentation = action.presentation
        if (!presentation.visible) return

        val menuItem: MenuItem
        if (action is ActionGroup) {
            val subMenu = menu.addSubMenu(presentation.title)

            action.getChildren(data)?.filter { it.location == action.location }?.forEach { child ->
                child.update(data)
                fillMenu(subMenu, child, data)
            }
            menuItem = subMenu.item
        } else {
            menuItem = menu.add(presentation.title)
        }

        if (presentation.icon != 0) {
            menuItem.setIcon(presentation.icon)
        }
        menuItem.isEnabled = presentation.enabled
        menuItem.setShowAsAction(presentation.showAsAction)

        menuItem.setOnMenuItemClickListener {
        runCatching {
        action.performAction(data)
    	}.onFailure {
    	
    	}.isSuccess
            
        }
    }


    override fun registerAction(action: Action) {
        _actions[action.id] = action
    }

    override fun unregisterAction(action: Action) {
        _actions.remove(action.id)
    }

    override fun clear() {
        _actions.clear()
    }
}
