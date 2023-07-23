package com.ardev.api.actions

import android.view.MenuItem
import androidx.annotation.DrawableRes

data class Presentation(
  var title: String = "",
  @DrawableRes var icon: Int = 0,
  var visible: Boolean = true,
  var enabled: Boolean = true,
  private var _showAsAction: Int = MenuItem.SHOW_AS_ACTION_NEVER
) {
  var showAsAction: Int
  get() = _showAsAction
  set(value) {
    _showAsAction = when (value) {
      MenuItem.SHOW_AS_ACTION_NEVER,
      MenuItem.SHOW_AS_ACTION_IF_ROOM,
      MenuItem.SHOW_AS_ACTION_ALWAYS,
      MenuItem.SHOW_AS_ACTION_WITH_TEXT,
      MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW ->
      value
      else -> MenuItem.SHOW_AS_ACTION_NEVER
    }
  }
}