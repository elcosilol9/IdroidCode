package com.ardev.tools.parser

import java.io.File

class GradleParser(private val file: File) {
	 val content by lazy { file.readText() }
    private val dependencyRegex = Regex("""implementation\s+['"]([^'"]+)['"]""")
    private val versionNameRegex = Regex("""versionName\s+"([^"]+)"""")
    private val applicationIdRegex = Regex("""applicationId\s+"([^"]+)"""")

    fun findDependency(group: String, name: String): String? = dependencies.find { it.startsWith("$group:$name:") }
    
    val dependencies: List<String> get() = dependencyRegex.findAll(content).map { it.groupValues[1] }.toList()
    
    val versionName: String? get() = versionNameRegex.find(content)?.groupValues?.get(1)
    
	val applicationId: String? get() = applicationIdRegex.find(content)?.groupValues?.get(1)
    
}