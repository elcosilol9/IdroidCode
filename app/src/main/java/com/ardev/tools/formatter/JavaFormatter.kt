package com.ardev.tools.formatter

import java.util.Arrays
import java.util.stream.Collectors

class JavaFormatter {
    private var source: String = ""
    private val result = StringBuilder()

    fun format(source: String): String {
        this.source = source
        val charArray = trimSource().toCharArray()
        val length = charArray.size
        var index = 0
        var indents = 0

        var isSingleLineComment = false
        var isMultiLineComment = false
        var isJavaDoc = false
        var isEscape = false
        var isChar = false
        var isString = false

        while (index < length) {
            val currentChar = charArray[index]
            val nextCharIndex = index + 1
            val isValidNextChar = isValidIndex(nextCharIndex, charArray)

            if (isSingleLineComment) {
                if (currentChar == '\n') {
                    result.append(currentChar)
                    addIndent(indents)
                    isSingleLineComment = false
                } else {
                    result.append(currentChar)
                }
            } else if (isEscape) {
                result.append(currentChar)
                isEscape = false
            } else if (currentChar == '\\') {
                result.append(currentChar)
                isEscape = true
            } else if (isChar) {
                if (currentChar == '\'') {
                    result.append(currentChar)
                    isChar = false
                } else {
                    result.append(currentChar)
                }
            } else if (isString) {
                if (currentChar == '\"') {
                    result.append(currentChar)
                    isString = false
                } else {
                    result.append(currentChar)
                }
            } else {
                if (isMultiLineComment) {
                    if (currentChar == '*') {
                        if (isValidNextChar) {
                            val nextChar = charArray[nextCharIndex]
                            if (nextChar == '/') {
                                isMultiLineComment = false
                                isJavaDoc = false
                            }
                        }
                    }
                } else {
                    if (currentChar == '/') {
                        if (isValidNextChar) {
                            val nextChar = charArray[nextCharIndex]
                            if (nextChar == '/') {
                                result.append(currentChar)
                                result.append(nextChar)
                                isSingleLineComment = true
                                index = nextCharIndex + 1
                                continue
                            }
                            if (nextChar == '*') {
                                result.append(currentChar)
                                result.append(nextChar)
                                isMultiLineComment = true
                                index = nextCharIndex + 1
                                continue
                            }
                        }
                    }

                    if (currentChar == '\'') {
                        isChar = true
                    }

                    if (currentChar == '\"') {
                        isString = true
                    }
                }

                if (!isJavaDoc) {
                    if (currentChar == '{') {
                        ++indents
                    }

                    if (currentChar == '}') {
                        --indents
                        if (result[result.length - 1] == '\t') {
                            result.deleteCharAt(result.length - 1)
                        }
                    }
                }

                result.append(currentChar)

                if (currentChar == '\n') {
                    addIndent(indents)
                    if (isMultiLineComment) {
                        if (isValidNextChar) {
                            val nextChar = charArray[nextCharIndex]
                            if (nextChar == '*') {
                                isJavaDoc = true
                                result.append(" ")
                            } else {
                                isJavaDoc = false
                            }
                        }
                    }
                    if (isValidNextChar) {
                        val nextChar = charArray[nextCharIndex]
                        if (nextChar == '.' || nextChar == '?' || nextChar == ':' || nextChar == '&' || nextChar == '|' || nextChar == '+') {
                            result.append("\t")
                        }
                    }
                }
            }
            ++index
        }
        return result.toString()
    }

    private fun isValidIndex(index: Int, source: CharArray): Boolean {
        return index < source.size && index > -1
    }

    private fun addIndent(indents: Int) {
        repeat(indents) {
            result.append('\t')
        }
    }

    private fun trimSource(): String {
        return Arrays.stream(source.split("\n"))
            .map(String::trim)
            .collect(Collectors.joining("\n"))
    }
}