package com.ardev.tools.depends.repository

import java.io.File
import java.io.InputStream

class LocalRepository(override val name: String) : Repository {

    override var cacheDirectory: File = File("")
        get() = field
    	set(value) {
    	field = value
	}
	
    override fun getInputStream(path: String): InputStream? {
        return getFile(path)?.inputStream() ?: null   
    }

    override fun getFile(path: String): File? {
            rootDirectory.resolveDirs
        return File(rootDirectory, path).takeIf { it.exists() }
    }
    
    override fun getCachedFile(path: String): File? {
           rootDirectory.resolveDirs
        return File(rootDirectory, path).takeIf { it.exists() }
    }

    fun save(path: String, inputStream: InputStream): File {
  	 	val file = File(rootDirectory, path)
    	file.parentFile.resolveDirs
        inputStream saveTo file
    return file
    }

}