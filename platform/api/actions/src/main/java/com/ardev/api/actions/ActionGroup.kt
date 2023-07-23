package com.ardev.api.actions

abstract class ActionGroup : Action() {

    override fun performAction(data: ActionData) {}

    abstract fun getChildren(data: ActionData?): Array<Action>?
}