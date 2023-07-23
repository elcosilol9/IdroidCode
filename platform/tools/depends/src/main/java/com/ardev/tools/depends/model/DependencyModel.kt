package com.ardev.tools.depends.model

data class DependencyModel(
    var groupId: String = "",
    var artifactId: String = "",
    var versionName: String = "",
    var scope: String = "",
    var type: String = "",
    var excludes: List<DependencyModel> = ArrayList()
) {
    companion object {
        fun valueOf(declaration: String): DependencyModel {
            val names = declaration.split(":")
            if (names.size < 3) {
                throw IllegalStateException("Unknown format: $declaration")
            }
            return DependencyModel(groupId = names[0],
            artifactId = names[1], 
             versionName = names[2]
             )
        }
    }

    val latestVersion: String get() {
        var temp = versionName.replaces("[" to "", "]" to "", "(" to "", ")" to "")
        if (temp.contains(",")) {           
            temp.split(",").forEach {
              if (!it.isEmpty()) return it              
                }
             }
        return temp
    }
  
    override fun toString(): String {
        return "$groupId:$artifactId:$versionName"
    }
}