package com.ardev.idroid.common.ext

infix fun String?.default(text: String): String = if(!this.isNullOrBlank()) this else text 
 
fun String.startsWith(vararg suffix: String): Boolean {
    suffix.forEach {
       if (this.startsWith(it, false)) return true
    }
    return false
}

fun String.endsWith(vararg prefix: String): Boolean {
    prefix.forEach {
       if (this.endsWith(it, false)) return true
    }
    return false
}

fun String.replaces(vararg content: Pair<String, String>): String {
    var result = this
    content.forEach {
   result = result.replace(it.first, it.second)      
    }
    return result 
}