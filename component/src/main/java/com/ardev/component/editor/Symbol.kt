package com.ardev.component.editor

data class Symbol(
        val label: String,
        val commit: String = label,
        val offset: Int = 1
    )