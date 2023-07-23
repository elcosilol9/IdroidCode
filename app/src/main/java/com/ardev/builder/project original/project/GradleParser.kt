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
        val compileSdkVersion = extractCompileSdkVersion(lines)
        val buildToolsVersion = extractBuildToolsVersion(lines)
        val minSdkVersion = extractMinSdkVersion(lines)
        val targetSdkVersion = extractTargetSdkVersion(lines)
        val applicationId = extractApplicationId(lines)
        val dependencies = extractDependencies(lines)
        return BuildConfig(
            packageName,
            versionCode,
            versionName,
            compileSdkVersion,
            buildToolsVersion,
            minSdkVersion,
            targetSdkVersion,
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

    private fun extractCompileSdkVersion(lines: List<String>): Int {
        // Extraer el compileSdkVersion
    }

    private fun extractBuildToolsVersion(lines: List<String>): String {
        // Extraer la versión de buildTools
    }

    private fun extractMinSdkVersion(lines: List<String>): Int {
        // Extraer el minSdkVersion
    }

    private fun extractTargetSdkVersion(lines: List<String>): Int {
        // Extraer el targetSdkVersion
    }

    private fun extractApplicationId(lines: List<String>): String {
        // Extraer el applicationId
    }

    private fun extractDependencies(lines: List<String>): List<String> {
        // Extraer las dependencias
    }
}

fun main() {
    val buildFile = File("path/to/build.gradle")
    val buildParser = BuildParser(buildFile)
    val buildConfig = buildParser.parseBuildConfig()
    println("Package Name: ${buildConfig.packageName}")
    println("Version Code: ${buildConfig.versionCode}")
    println("Version Name: ${buildConfig.versionName}")
    println("Compile SDK Version: ${buildConfig.compileSdkVersion}")
    println("Build Tools Version: ${buildConfig.buildToolsVersion}")
    println("Min SDK Version: ${buildConfig.minSdkVersion}")
    println("Target SDK Version: ${buildConfig.targetSdkVersion}")
    println("Application ID: ${buildConfig.applicationId}")
    println("Dependencies: ${buildConfig.dependencies}")
}
