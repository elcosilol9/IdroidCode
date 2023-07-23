package com.ardev.idroid.ui.main.model

import java.io.File
import java.io.Serializable
import java.nio.charset.StandardCharsets
import java.util.*

data class EditorModel(val file: File, val preview: Boolean = false) : Serializable {
    val id: String = generateIdFromFile(file, preview)

    companion object {
        private fun generateIdFromFile(file: File, isPreview: Boolean): String {
            val key = "${file.absolutePath}${if (isPreview) "-preview" else ""}"
            return UUID.nameUUIDFromBytes(key.toByteArray(StandardCharsets.UTF_8)).toString()
        }
    }   
}