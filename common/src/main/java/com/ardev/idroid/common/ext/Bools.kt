package com.ardev.idroid.common.ext

val Boolean.int
    get() = if (this) 1 else 0
