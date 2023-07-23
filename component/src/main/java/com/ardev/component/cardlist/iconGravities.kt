package com.ardev.component.cardlist

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

object Icon {
        const val GRAVITY_TOP = 0
        const val GRAVITY_MIDDLE = 1
        const val GRAVITY_BOTTOM = 2
}

@Retention(RetentionPolicy.SOURCE)
@IntDef(Icon.GRAVITY_TOP, Icon.GRAVITY_MIDDLE, Icon.GRAVITY_BOTTOM)
annotation class IconGravity