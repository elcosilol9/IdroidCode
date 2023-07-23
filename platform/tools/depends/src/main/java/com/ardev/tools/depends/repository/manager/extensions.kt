package com.ardev.tools.depends.repository.manager

val PomModel.libraryPath: String get() = buildString {
    append(groupId.replace('.', '/'))
    append('/')
    append(artifactId)
    append('/')
    append(versionName)
    append('/')
    append(artifactId)
    append('-')
    append(versionName)
    append(if (packaging == "aar") ".aar" else ".jar")
}


val String.parsePomDeclaration: Array<String> get() {
    var declaration = this
    if (declaration.endsWith(".pom")) {
        declaration = declaration.substring(0, declaration.length - 4)
    }
    val strings = declaration.split(":").toTypedArray()
    return if (strings.size < 3) arrayOf() else strings
}


val Array<String>.pathFromDeclaration: String get() = if (!this.isEmpty()) {
    val groupId = this[0].replace('.', '/')
    val artifactId = this[1]
    val versionName = this[2]
    "$groupId/$artifactId/$versionName/$artifactId-$versionName"
} else ""
