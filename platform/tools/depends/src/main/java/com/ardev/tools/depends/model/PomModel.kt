package com.ardev.tools.depends.model

data class PomModel(
    var artifactId: String = "",
    var groupId: String = "",
    var versionName: String = "",
    var packaging: String = "",
    var userDefined: Boolean = false,
    var dependencies: List<DependencyModel> = ArrayList(),
    var excludes: List<DependencyModel> = ArrayList(),
    var properties: MutableMap<String, String> = HashMap(),
    var parent: PomModel? = null,
    var managedDependencies: List<DependencyModel> = ArrayList()
) {
    companion object {       
        fun valueOf(declaration: String): PomModel {
            val names = declaration.split(":")
            if (names.size < 3) {
                throw IllegalStateException("Unknown format: $declaration")
            }
            return PomModel().apply {
                groupId = names[0]
                artifactId = names[1]
                versionName = names[2]
            }
        }
    }
    
    val fileName = "$artifactId-$versionName"

    val path: String get() {
        val mPath = groupId.replace('.', '/')
        return "$mPath/$artifactId/$versionName"
    }   

    fun addProperty(key: String, value: String) {
        properties[key] = value
    }

    fun getProperty(key: String): String? {
        return properties[key]
    }    
    
    override fun toString(): String = "$groupId:$artifactId:$versionName"
}