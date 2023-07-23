package com.ardev.idroid.common.ext


infix fun Int.default(def: Int): Int = if(this != 0) this else def 