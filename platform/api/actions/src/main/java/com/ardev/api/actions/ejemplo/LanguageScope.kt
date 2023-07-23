package com.raredev.vcspace.models

import java.io.File

enum class LanguageScope(val extension: String, val scope: String) {
    CS("c#", "source.cs"),
    CSS("css", "source.css"),
    CPP("cpp", "source.cpp"),
    C("c", "source.cpp"),
    GRADLE("gradle", "source.groovy"),
    GROOVY("groovy", "source.groovy"),
    GO("go", "source.go"),
    H("h", "source.cpp"),
    HTML("html", "text.html.basic"),
    INI("ini", "source.ini"),
    JAVA("java", "source.java"),
    JSON("json", "source.json"),
    JAVASCRIPT("js", "source.js"),
    SHELLSCRIPT("sh", "source.shell"),
    KT("kt", "source.kotlin"),
    KTS("kts", "source.kotlin"),
    LUA("lua", "source.lua"),
    MD("md", "text.html.markdown"),
    PHP("php", "source.php"),
    PYTHON("py", "source.python"),
    RMD("Rmd", "text.html.markdown"),
    YAML("yaml", "source.yaml"),
    YML("yml", "source.yaml"),
    XML("xml", "text.xml"),
    UNKNOWN("", "");

    companion object {
        fun forFile(file: File?): LanguageScope {
            val extension = file?.extension
            return values().find { it.extension == extension } ?: UNKNOWN
        }
    }
}
