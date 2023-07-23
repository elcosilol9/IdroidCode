package com.ardev.component.cardlist

import java.util.UUID
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.annotation.StringRes

class ActionItem(
	val title: String? = null,
	@StringRes val titleRes: Int = 0,
	val desc: String? = null,
	@StringRes val descRes: Int = 0,
	val icon: Drawable? = null,
	@DrawableRes val iconRes: Int = 0,
    @IconGravity val iconGravity: Int = Icon.GRAVITY_MIDDLE,
    val showIcon: Boolean = true,
    val onClick : (ActionItem, Int) -> Unit? = null
) : BaseItem()

class TitleItem(
	val title: String? = null,
	@StringRes val titleRes: Int = 0,
	val desc: String? = null,
	@StringRes val descRes: Int = 0,
	val icon: Drawable? = null,
	@DrawableRes val iconRes: Int = 0,
	val onClick : (TitleItem, Int) -> Unit? = null
) : BaseItem() 

abstract class BaseItem {
    val id: String = UUID.randomUUID().toString()
}