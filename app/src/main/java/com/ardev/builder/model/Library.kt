package com.ardev.builder.model

import com.google.gson.annotations.SerializedName
import java.io.File

data class Library(
    @SerializedName("sourceFile")
    val sourceFile: String,
    @SerializedName("declaration")
    var declaration: String?
) {
    fun getSourceFile(): File = File(sourceFile)

    fun isDependency(): Boolean = declaration != null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Library) return false
        return sourceFile == other.sourceFile
    }

    override fun hashCode(): Int = sourceFile.hashCode()

    override fun toString(): String = "sourceFile = $sourceFile"
}
