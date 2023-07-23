package com.ardev.idroid.common.ext

import android.util.Base64
import java.security.MessageDigest

val String.encode get() = Base64.encodeToString(toByteArray(), Base64.DEFAULT)
        
val String.decode get() = Base64.decode(this, Base64.DEFAULT).decodeToString()

val String.md5: String get() = kotlin.runCatching {
    val instance = MessageDigest.getInstance("MD5")
    val digest = instance.digest(this.toByteArray())
    val sb = StringBuffer()
    for (b in digest) {
        val i = b.toInt() and 0xff
        var hexString = Integer.toHexString(i)
        if (hexString.length < 2) {
            hexString = "0$hexString"
        }
        sb.append(hexString)
    }
    sb.toString()
}.getOrNull() ?: this