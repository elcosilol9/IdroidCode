package com.ardev.idroid.common.ext

val String.isHexFormat get() = matches("\\p{XDigit}+".toRegex())

val String.isValidColor get() = isValidFullColor || isValidAbbrColor

val String.normalizeColor get() =  if (isValidAbbrColor) expandAbbrColor else this


private val String.expandAbbrColor get() = toCharArray()
.mapIndexed {
  i, c -> if (i == 0) c.toString() else c.toString() + c
    }.joinToString(separator = "")

private val String.isValidFullColor get() = matches("^#([0-9a-fA-F]{6}|[0-9a-fA-F]{8})$".toRegex())


private val String.isValidAbrrColor get() = matches("^#([0-9a-fA-F]{3}|[0-9a-fA-F]{4})$".toRegex())
