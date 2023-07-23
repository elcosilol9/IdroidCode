package com.ardev.builder.parser

import java.io.File

data class BuildConfig(
    val packageName: String,
    val versionCode: Int,
    val versionName: String,
    val compileSdkVersion: Int,
    val buildToolsVersion: String,
    val minSdkVersion: Int,
    val targetSdkVersion: Int,
    val applicationId: String,
    val dependencies: List<String>
)

class BuildParser(private val buildFile: File) {
    fun parseBuildConfig(): BuildConfig {
        val lines = buildFile.readLines()
        val packageName = extractPackageName(lines)
        val versionCode = extractVersionCode(lines)
        val versionName = extractVersionName(lines)
        val compileSdk = extractCompileSdk(lines)
        val buildToolsVersion = extractBuildToolsVersion(lines)
        val minSdk = extractMinSdk(lines)
        val targetSdk = extractTargetSdk(lines)
        val applicationId = extractApplicationId(lines)
        val dependencies = extractDependencies(lines)
        return BuildConfig(
            packageName,
            versionCode,
            versionName,
            compileSdk,
            buildToolsVersion,
            minSdk,
            targetSdk,
            applicationId,
            dependencies
        )
    }

    private fun extractPackageName(lines: List<String>): String {
        // Extraer el nombre del paquete
    }

    private fun extractVersionCode(lines: List<String>): Int {
        // Extraer el código de versión
    }

    private fun extractVersionName(lines: List<String>): String {
        // Extraer el nombre de versión
    }

    private fun extractCompileSdk(lines: List<String>): Int {
        // Extraer el compileSdkVersion
    }

    private fun extractBuildToolsVersion(lines: List<String>): String {
        // Extraer la versión de buildTools
    }

    private fun extractMinSdk(lines: List<String>): Int {
        // Extraer el minSdkVersion
    }

    private fun extractTargetSdk(lines: List<String>): Int {
        // Extraer el targetSdkVersion
    }

    private fun extractApplicationId(lines: List<String>): String {
        // Extraer el applicationId
    }

    private fun extractDependencies(lines: List<String>): List<String> {
        // Extraer las dependencias
    }
}


