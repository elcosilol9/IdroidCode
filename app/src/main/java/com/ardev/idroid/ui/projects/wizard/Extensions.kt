package com.ardev.idroid.ui.home.wizard

import java.util.List

val Template.sdkList: List<String> 
		get() {
            val list = mapOf(
                21 to "API 21: Android 5.0 (Lollipop)",
                22 to "API 22: Android 5.1 (Lollipop)",
                23 to "API 23: Android 6.0 (Marshmallow)",
                24 to "API 24: Android 7.0 (Nougat)",
                25 to "API 25: Android 7.1 (Nougat)",
                26 to "API 26: Android 8.0 (Oreo)",
                27 to "API 27: Android 8.1 (Oreo)",
                28 to "API 28: Android 9.0 (Pie)",
                29 to "API 29: Android 10.0 (Q)",
                30 to "API 30: Android 11.0 (R)",
                31 to "API 31: Android 12.0 (S)"
            )
            return (minSdk..31).mapNotNull { list[it] }
        }
    
val Template.languageList: List<String> 
 	get() {
        val list = mutableListOf<String>()
        if (isJavaSupported) list.add("Java")
        if (isKotlinSupported) list.add("Kotlin")
        return list
    }