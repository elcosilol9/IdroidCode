package com.ardev.tools.depends.repository

enum class FileType(private val mExtension: String) {
    JAR("jar"),
    AAR("aar"),
    SOURCE_JAR("jar"),
    POM("pom");

    val extension = mExtension
}