package com.ardev.idroid.common.ext

import dalvik.system.BaseDexClassLoader


class MultipleDexClassLoader(private val librarySearchPath: String? = null) {
    val loader by lazy {
        BaseDexClassLoader("", null, librarySearchPath, javaClass.classLoader)
    }


    private val addDexPath = BaseDexClassLoader::class.java
        .getMethod("addDexPath", String::class.java)

    fun loadDex(dexPath: String): BaseDexClassLoader {
        addDexPath.invoke(loader, dexPath)

        return loader
    }
}